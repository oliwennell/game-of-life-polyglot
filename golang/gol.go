package main

import "fmt"

type Cell struct {
	IsAlive bool
	X, Y    int
}

type Row struct {
	Cells []Cell
}

type Grid struct {
	Rows []Row
}

func SimulateGrid(grid Grid) Grid {
	var newRows []Row
	for _, row := range grid.Rows {
		newRows = append(newRows, SimulateRow(row, grid))
	}
	return Grid{Rows: newRows}
}

func SimulateRow(row Row, grid Grid) Row {
	var newCells []Cell
	for _, cell := range row.Cells {
		newCells = append(newCells, SimulateCell(cell, grid))
	}
	return Row{Cells: newCells}
}

func SimulateCell(cell Cell, grid Grid) Cell {
	var numLiveNeighbours = CountLiveNeighbours(cell, grid)
	var shouldLiveOn = ShouldLiveOn(cell.IsAlive, numLiveNeighbours)
	return Cell{IsAlive: shouldLiveOn, X: cell.X, Y: cell.Y}
}

func ShouldLiveOn(isAlive bool, numLiveNeighbours int) bool {
	if isAlive {
		return numLiveNeighbours == 2 || numLiveNeighbours == 3
	}

	return numLiveNeighbours == 3
}

func CountLiveNeighbours(cell Cell, grid Grid) int {
	size := len(grid.Rows)
	result := 0

	for _, dx := range []int{-1, 0, 1} {
		for _, dy := range []int{-1, 0, 1} {
			neighbourX := cell.X + dx
			neighbourY := cell.Y + dy

			if neighbourX == cell.X && neighbourY == cell.Y {
				continue
			}

			inBounds := neighbourX >= 0 && neighbourX < size && neighbourY >= 0 && neighbourY < size

			if inBounds {
				neighbourCell := grid.Rows[neighbourY].Cells[neighbourX]
				if neighbourCell.IsAlive {
					result++
				}
			}
		}
	}

	//fmt.Println(fmt.Sprintf("%d, %d => %d", cell.X, cell.Y, result))
	return result
}

func CellToString(cell Cell) string {
	if cell.IsAlive {
		return "X"
	}

	return "_"
}

func PrintGrid(grid Grid) {
	for _, row := range grid.Rows {
		for _, cell := range row.Cells {
			fmt.Printf("%s", CellToString(cell))
		}
		fmt.Println("")
	}
}

func main() {
	var grid = Grid{Rows: []Row{
		Row{Cells: []Cell{Cell{IsAlive: false, X: 0, Y: 0}, Cell{IsAlive: true, X: 1, Y: 0}, Cell{IsAlive: false, X: 2, Y: 0}}},
		Row{Cells: []Cell{Cell{IsAlive: false, X: 0, Y: 1}, Cell{IsAlive: true, X: 1, Y: 1}, Cell{IsAlive: false, X: 2, Y: 1}}},
		Row{Cells: []Cell{Cell{IsAlive: false, X: 0, Y: 2}, Cell{IsAlive: true, X: 1, Y: 2}, Cell{IsAlive: false, X: 2, Y: 2}}}}}

	PrintGrid(grid)
	for i := 0; i < len(grid.Rows); i++ {
		grid = SimulateGrid(grid)

		fmt.Println("")
		PrintGrid(grid)
	}
}
