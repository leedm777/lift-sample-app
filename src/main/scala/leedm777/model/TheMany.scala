package leedm777.model

import net.liftweb.mapper._

/** Many side of a many-to-one relationship */
class TheMany extends LongKeyedMapper[TheMany] with IdPK {
  def getSingleton = TheMany

  object theOne extends LongMappedMapper(this, TheOne)
}

object TheMany extends TheMany with LongKeyedMetaMapper[TheMany] {

}
