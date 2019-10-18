
object Main extends App {

  type Grid = List[List[Cell]]
  
  abstract class Cell(var x: Int, var y: Int)
  class AliveCell(x: Int, y: Int) extends Cell(x, y)
  class DeadCell(x: Int, y: Int) extends Cell(x, y)

  val initialCells = List[List[Cell]](
    List(new AliveCell(0,0), new DeadCell(1,0),  new DeadCell(2,0),  new DeadCell(3,0)),
    List(new DeadCell(0,1),  new AliveCell(1,1), new DeadCell(2,1),  new DeadCell(3,1)),
    List(new DeadCell(0,2),  new DeadCell(1,2),  new AliveCell(2,2), new DeadCell(3,2)),
    List(new DeadCell(0,3),  new DeadCell(1,3),  new DeadCell(2,3),  new AliveCell(3,3)),
  )

  def countLiveNeighbours(cell: Cell, grid: Grid) =
    3

  def shouldLiveOn(cell: Cell, grid: Grid) = {
    val numLiveNeighbours = countLiveNeighbours(cell, grid)
    cell match {
      case _: AliveCell => numLiveNeighbours == 2 || numLiveNeighbours == 3
      case _: DeadCell => numLiveNeighbours == 3
    }
  }

  def simulateRow(row: List[Cell], grid: Grid) =
    row.map(cell => shouldLiveOn(cell, grid) match {
      case true => new AliveCell(cell.x, cell.y)
      case false => new DeadCell(cell.x, cell.y)
    })

  def simulate(grid: Grid) = 
    grid.map(row => simulateRow(row, grid))

  def cellToString(cell: Cell) = 
    cell match {
      case _: DeadCell => " "
      case _: AliveCell => "X"
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