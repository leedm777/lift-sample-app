package leedm777.snippet

import leedm777.model.User
import net.liftweb.http.{S, LiftScreen}

/**
 *  Demonstrates some challenges with getting FormParam to work.  Fields with class="has-class"
 *  will not be displayed.  Just go to http://localhost:8080 to see this screen.
 */
object FormParamScreen extends LiftScreen {

  object user extends ScreenVar(User.create.email("user@nowhere.com"))

  /**Should have class="has-class", but does not */
  val f = field("field", "field", FormParam("class" -> "has-class"))

  /**Works as expected */
  val p = password("password", "password", FormParam("class" -> "has-class"))

  /**Works as expected */
  val s = select("select", "select", List("select", "one", "dos", "trois"), FormParam("class" -> "has-class"))

  /**Works as expected */
  val t = text("text", "text", FormParam("class" -> "has-class"))

  /**No idea how to set the class on this field */
  addFields(() => user.is.email)

  protected def finish() {
    S.notice("Sure; I'll finish that later")
  }
}
