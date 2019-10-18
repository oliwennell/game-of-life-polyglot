
object Main extends App {

  type Grid = List[List[Cell]]
  
  abstract class Cell
  case class AliveCell() extends Cell
  case class DeadCell() extends Cell

  val initialCells = List[List[Cell]](
    List(new AliveCell, new DeadCell,  new DeadCell,  new DeadCell),
    List(new DeadCell,  new AliveCell, new DeadCell,  new DeadCell),
    List(new DeadCell,  new DeadCell,  new AliveCell, new DeadCell),
    List(new DeadCell,  new DeadCell,  new DeadCell,  new AliveCell),
  )

  def shouldLiveOn(cell: Cell, grid: Grid) =
    cell match {
      case AliveCell() => true
      case DeadCell() => false
    }

  def simulateRow(row: List[Cell], grid: Grid) =
    row.map(cell => shouldLiveOn(cell, grid))
       .map(s => s match {
         case true => new AliveCell
         case false => new DeadCell
      })

  def simulate(grid: Grid) = 
    grid.map(row => simulateRow(row, grid))

  def cellToString(cell: Cell) = 
    cell match {
      case DeadCell() => " "
      case AliveCell() => "X"
    }

  def rowToString(row: List[Cell]) =
    row.map(cellToString)
       .reduce((a, b) => a + b)

  def printGrid(cells: Grid) {
    val rowStrings = cells.map(rowToString)
    var all = rowStrings.reduce((a,b) => a + "\n" + b)
    println(all)
  }

  var cells = initialCells;
  for (i <- 0 to 5) {
    cells = simulate(cells)
    printGrid(cells)
  }
}