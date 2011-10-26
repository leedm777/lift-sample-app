package leedm777
package snippet

import net.liftweb.http.{S, LiftScreen}

object Selection {
  // In the Real World™, this would come from a database or something
  // This is not the real world.
  var value: Seq[String] = Nil
}

class ShowMultiselect extends LiftScreen {
  val sel = multiselect(
    name = "Multiselect",
    default = Selection.value,
    choices = List("One", "Two", "Three", "Four", "Five"))

  def finish() {
    Selection.value = sel.is
    S.notice(sel.is.mkString("[", ", ", "]"))
  }
}

