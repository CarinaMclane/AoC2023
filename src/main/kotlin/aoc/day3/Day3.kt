package aoc.day3

import aoc.util.Helpers

class Day3 {
    fun solveTask1() : Int {
        val fileName = "src/main/kotlin/aoc/day3/input"

        val input = Helpers.readFile(fileName)
        val schematic = Schematic(input!!)

        return schematic.getPartNumbers().sum()
    }

    fun solveTask2() : Int {
        val fileName = "src/main/kotlin/aoc/day3/input"

        val input = Helpers.readFile(fileName)
        val schematic = Schematic(input!!)

        var sum = 0
        for (gearRatio in schematic.getGearRatios()) {
            var gearRatioValue = 1
            for (number in gearRatio) {
                gearRatioValue *= number
            }
            sum += gearRatioValue
        }

        return sum
    }
}