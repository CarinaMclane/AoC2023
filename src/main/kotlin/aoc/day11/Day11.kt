package aoc.day11

import aoc.util.Helpers

class Day11 {
    val fileName = "src/main/kotlin/aoc/day11/input"
    val input = Helpers.readFile(fileName)

    fun solveTask1() : Long {
        val galaxyMap = GalaxyMap(input!!)
        val distances = galaxyMap.calcGalaxyDistances(galaxyMap.getGalaxyLocations())
        return distances.sum()
    }

    fun solveTask2() : Long {
        val galaxyMap = GalaxyMap(input!!)
        val distances = galaxyMap.calcGalaxyDistances(galaxyMap.getGalaxyLocations(),1000000)
        return distances.sum()
    }
}