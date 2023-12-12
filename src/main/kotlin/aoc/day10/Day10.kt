package aoc.day10

import aoc.util.Helpers

class Day10 {
    val fileName = "src/main/kotlin/aoc/day10/input"
    val input = Helpers.readFile(fileName)
    val pipeSystem = PipeSystem(input!!.toMutableList())

    fun solveTask1(): Int {
        return pipeSystem.getFurthestPoint()
    }

    fun solveTask2(): Int {
        return pipeSystem.fillInsideSpacesNew()
    }
}