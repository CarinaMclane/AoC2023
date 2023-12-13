package aoc.day11

import aoc.util.Helpers

class Day11 {
    val fileName = "src/main/kotlin/aoc/day11/input"
    val input = Helpers.readFile(fileName)

    fun solveTask1() : Int {
        val galaxyMap = GalaxyMap(input!!)
        val galaxyLocations = galaxyMap.getGalaxyLocations()
        galaxyLocations.forEachIndexed{i,it ->  println("${i+1} : $it") }
        val distances = galaxyMap.calcGalaxyDistances(galaxyMap.getGalaxyLocations())
        return distances.sum()
    }
}