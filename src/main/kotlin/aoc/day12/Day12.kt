package aoc.day12

import aoc.util.Helpers

class Day12 {
    val fileName = "src/main/kotlin/aoc/day12/input"
    val input = Helpers.readFile(fileName)
    fun solveTask1() : Long {
        val combinations = SpringMap(input!!).getPossCombinations()
        return combinations
    }

    fun solveTask2() : Long {
        val combinations = SpringMap(input!!,5).getPossCombinations()
        return combinations
    }
}