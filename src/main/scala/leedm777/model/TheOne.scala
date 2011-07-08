package leedm777.model

import net.liftweb.mapper._

/** One side of a many-to-one relationship.  Note theMany is with cascade */
class TheOne extends LongKeyedMapper[TheOne] with IdPK with OneToMany[Long, TheOne] {
  def getSingleton = TheOne

  object theMany extends MappedOneToMany(TheMany, TheMany.theOne, OrderBy(TheMany.id, Ascending)) with Cascade[TheMany]
}

object TheOne extends TheOne with LongKeyedMetaMapper[TheOne]
