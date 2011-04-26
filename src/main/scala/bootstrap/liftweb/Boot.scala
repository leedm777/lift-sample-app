

package bootstrap.liftweb

import net.liftweb._
import http.{LiftRules, NotFoundAsTemplate, ParsePath, RedirectResponse}
import sitemap.{SiteMap, Menu, Loc}
import util.{ NamedPF }
import _root_.net.liftweb.sitemap.Loc._
import net.liftweb._
import mapper.{Schemifier, DB, StandardDBVendor, DefaultConnectionIdentifier}
import util.{Props}
import common.{Full,Loggable}
import http.{S,Req}
import leedm777.model._


class Boot extends Loggable {
  def boot {
  
    if (!DB.jndiJdbcConnAvailable_?) {
      val vendor = 
        new StandardDBVendor(Props.get("db.driver") openOr "org.h2.Driver",
        			               Props.get("db.url") openOr 
        			               "jdbc:h2:lift_proto.db;AUTO_SERVER=TRUE",
        			               Props.get("db.user"), Props.get("db.password"))

      LiftRules.unloadHooks.append(vendor.closeAllConnections_! _)

      DB.defineConnectionManager(DefaultConnectionIdentifier, vendor)
    }

    // Use Lift's Mapper ORM to populate the database
    // you don't need to use Mapper to use Lift... use
    // any ORM you want
    Schemifier.schemify(true, Schemifier.infoF _, User)
  
    // where to search snippet
    LiftRules.addToPackages("leedm777")

    // build sitemap
    val entries = List(Menu("Home") / "index") :::
                  List(Menu(Loc("Static", Link(List("static"), true, "/static/index"), 
                       "Static Content"))) :::
                  // the User management menu items
                  User.sitemap :::
                  Nil
    
    LiftRules.uriNotFound.prepend(NamedPF("404handler"){
      case (req,failure) => NotFoundAsTemplate(
        ParsePath(List("exceptions","404"),"html",false,false))
    })

    def isLoggedIn = User.loggedIn_?
    
    // The case statement passed into SiteMap should cause every request when
    // not logged in to redirect to the login screen.  In fact, it only
    // redirects the first page you hit.  And it always redirects that page,
    // even after login.
    LiftRules.setSiteMap(new SiteMap(List({
      case Full(Req(path, _, _)) if !isLoggedIn && path != List("user_mgt", "login") =>
        logger.debug("Redirecting to login")
        Loc.EarlyResponse(() => Full(RedirectResponse("/user_mgt/login")))
    }), entries:_*))
    
    // set character encoding
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))
    
    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)
    // What is the function to test if a user is logged in?
    LiftRules.loggedInTest = Full(() => User.loggedIn_?)

    // Make a transaction span the whole HTTP request
    S.addAround(DB.buildLoanWrapper)
    
  }
}
