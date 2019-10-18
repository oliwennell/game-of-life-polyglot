
object Main extends App {

  type Grid = Array[Array[Cell]]
  
  class Coordinate(var x: Int, var y: Int)

  abstract class Cell(val location: Coordinate)
  class AliveCell(location: Coordinate) extends Cell(location)
  class DeadCell(location: Coordinate) extends Cell(location)

  val initialCells = Array[Array[Cell]](
    Array(new DeadCell(new Coordinate(0,0)), new AliveCell(new Coordinate(1,0)),  new DeadCell(new Coordinate(2,0)),  new DeadCell(new Coordinate(3,0))),
    Array(new DeadCell(new Coordinate(0,1)), new AliveCell(new Coordinate(1,1)), new DeadCell(new Coordinate(2,1)),  new DeadCell(new Coordinate(3,1))),
    Array(new DeadCell(new Coordinate(0,2)), new AliveCell(new Coordinate(1,2)),  new DeadCell(new Coordinate(2,2)), new DeadCell(new Coordinate(3,2))),
    Array(new DeadCell(new Coordinate(0,3)), new DeadCell(new Coordinate(1,3)),  new DeadCell(new Coordinate(2,3)),  new DeadCell(new Coordinate(3,3)))
  )

  def isInGrid(location: Coordinate, grid: Grid) =
    location.x >= 0 && location.x < 4 && location.y >= 0 && location.y < 4

  def countLiveNeighbours(cell: Cell, grid: Grid) = {
    val deltas = Array( (-1, 1), (0, 1), (1, 1), (-1, 0), (1, 0), (-1, -1), (0, -1), (1, -1) )
    val neighbours = deltas.map(tuple => new Coordinate(cell.location.x + tuple._1, cell.location.y + tuple._2))
          .filter(delta => isInGrid(delta, grid))

    // val ns = neighbours.map(n => s"(${n.x},${n.y})").reduce((a,b) => a+b)
    // println(s"neighbours of ${cell.location.x}, ${cell.location.y} are: ${ns}")

    neighbours.map(location => { 
            grid(location.y)(location.x) match {
              case _: AliveCell => 1
              case _: DeadCell => 0
            }
          })
          .reduce((a, b) => a + b)
  }

  def shouldLiveOn(cell: Cell, grid: Grid) = {
    val numLiveNeighbours = countLiveNeighbours(cell, grid)
    //println(s"cell at ${cell.location.x}, ${cell.location.y} has $numLiveNeighbours nbos")
    cell match {
      case _: AliveCell => numLiveNeighbours == 2 || numLiveNeighbours == 3
      case _: DeadCell => numLiveNeighbours == 3
    }
  }

  def simulateRow(row: Array[Cell], grid: Grid) =
    row.map(cell => shouldLiveOn(cell, grid) match {
      case true => new AliveCell(cell.location)
      case false => new DeadCell(cell.location)
    })

  def simulate(grid: Grid) = 
    grid.map(row => simulateRow(row, grid))

  def cellToString(cell: Cell) = 
    cell match {
      case _: DeadCell => "0"
      case _: AliveCell => "X"
    }

  def rowToString(row: Array[Cell]) =
    row.map(cellToString).reduce((a, b) => a + b)

  def printGrid(cells: Grid) {
    val rowStrings = cells.map(rowToString)
    var all = rowStrings.reduce((a,b) => a + "\n" + b)
    println("")
    println(all)
  }

  var cells = initialCells;
  for (i <- 0 to 5) {
    cells = simulate(cells)
    printGrid(cells)
  }
}