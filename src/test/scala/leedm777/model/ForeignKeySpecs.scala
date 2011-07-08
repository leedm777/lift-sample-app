package leedm777.model

import org.specs.Specification
import net.liftweb.db.StandardDBVendor
import net.liftweb.util.Props
import net.liftweb.http.LiftRules
import net.liftweb.mapper._

/**
 * Demonstrate a problem with cascade deletes when createForeignKeys_? is true.
 */
object ForeignKeySpecs extends Specification {
  if (!DB.jndiJdbcConnAvailable_?) {
    val vendor =
      new StandardDBVendor(Props.get("db.driver") openOr "org.h2.Driver",
        Props.get("db.url") openOr
          "jdbc:h2:lift_proto.db;AUTO_SERVER=TRUE",
        Props.get("db.user"), Props.get("db.password"))

    LiftRules.unloadHooks.append(vendor.closeAllConnections_! _)

    DB.defineConnectionManager(DefaultConnectionIdentifier, vendor)
  }

  // if you comment out the following line, the spec passed
  MapperRules.createForeignKeys_? = _ => true

  Schemifier.destroyTables_!!(Schemifier.neverF _, TheOne, TheMany)
  Schemifier.schemify(true, Schemifier.infoF _, TheOne, TheMany)

  "Deleting TheOne" should {
    "cascade delete TheMany" in {
      // create test objects
      val theOne = TheOne.create.saveMe()
      val theMany = TheMany.create.theOne(theOne).saveMe()
      // Mapper limitation - collection must be fetched for cascade delete to work
      theOne.theMany.refresh

      // kinda silly, but just make sure the records are in the database
      TheOne.count must_== 1
      TheMany.count must_== 1

      // deleting theOne should delete theMany
      // when FK's are used, this results in a Referential integrity constraint violation
      theOne.delete_! must_== true

      // now the tables should be emtpy
      TheOne.count must_== 0
      TheMany.count must_== 0
    }
  }
}
