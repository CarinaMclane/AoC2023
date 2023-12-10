package aoc.day5

import aoc.util.Helpers

class Day5 {
    val fileName = "src/main/kotlin/aoc/day5/input"
    val input = Helpers.readFile(fileName)

    fun solveTask1() : Long {
        val almanac = Almanac(input!!)
        val locations = almanac.getLocations()
        return locations.min()
    }

    fun solveTask2() : Long {
        val almanac = Almanac(input!!, true)
        return almanac.getSmallestRangeLocation()
    }
}