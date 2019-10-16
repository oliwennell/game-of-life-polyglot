
object Main extends App {

  val cells = List[List[String]](
    List("X", " ", " ", " "),
    List(" ", "X", " ", " "),
    List(" ", " ", "X", " "),
    List(" ", " ", " ", "X"),
  )

  def rowToString(row: List[String]) = row.reduce((a, b) => a + b)

  val rowStrings = cells.map(rowToString)
  var all = rowStrings.reduce((a,b) => a + "\n" + b)
  println(all)
}