package leedm777
package snippet

import java.util.Date
import net.liftweb.util.Helpers._

class HelloWorld {
  def howdy = "#now" #> (new Date).toString
}
