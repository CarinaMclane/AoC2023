package aoc.day2

import aoc.util.Helpers
import java.io.File
import java.lang.Exception

class Day2 {
    fun solveTask1() : Int {
        val bag = mapOf(
            "red" to 12,
            "green" to 13,
            "blue" to 14
        )

        val fileName = "src/main/kotlin/aoc/day2/input"

        val input = Helpers.readFile(fileName)

        var sum = 0

        for (line in input ?: listOf()) {
            val gameResult = isGamePossible(line, bag)
            if (gameResult.second)
                sum += gameResult.first
        }

        return sum
    }

    fun solveTask2() : Int {
        val fileName = "src/main/kotlin/aoc/day2/input"

        val input = Helpers.readFile(fileName)

        var sum = 0

        for (line in input ?: listOf()) {
            val gameResult = findCubesNeeded(line)
            sum += calculatePower(gameResult)
        }

        return sum
    }

    private fun calculatePower(gameResult: Pair<Int, Map<String, Int>>): Int {
        var result = 1
        for (colour in gameResult.second)
            result *= colour.value

        return result
    }



    private fun isGamePossible(gameInfo: String, bag: Map<String, Int>): Pair<Int,Boolean> {
        val gameIdRegex = Regex("\\d+")
        val gameId = gameIdRegex.find(gameInfo)?.value?.toIntOrNull() ?: -1
        val games = gameInfo.split(':').last().split(';')

        var isPossible = true

        for (game in games) {
            val cubeUse = mutableMapOf(
                "red" to 0,
                "green" to 0,
                "blue" to 0
            )

            val gameCubeInfo = game.split(',')
            for (cubeInfo in gameCubeInfo) {
                val numCubes = Regex("\\d+").find(cubeInfo)?.value?.toIntOrNull() ?: 0
                val colourKey = cubeInfo.findAnyOf(cubeUse.keys)?.second!!
                cubeUse[colourKey] = cubeUse[colourKey]!! + numCubes
            }

            for (colour in bag) {
                if (colour.value < cubeUse[colour.key]!!)
                    isPossible = false
            }
        }

        return Pair(gameId, isPossible)
    }

    private fun findCubesNeeded(gameInfo: String): Pair<Int,Map<String,Int>> {
        val gameIdRegex = Regex("\\d+")
        val gameId = gameIdRegex.find(gameInfo)?.value?.toIntOrNull() ?: -1
        val games = gameInfo.split(':').last().split(';')

        val fewestCubesNeeded = mutableMapOf(
            "red" to 0,
            "green" to 0,
            "blue" to 0
        )

        for (game in games) {
            val cubeUse = mutableMapOf(
                "red" to 0,
                "green" to 0,
                "blue" to 0
            )

            val gameCubeInfo = game.split(',')
            for (cubeInfo in gameCubeInfo) {
                val numCubes = Regex("\\d+").find(cubeInfo)?.value?.toIntOrNull() ?: 0
                val colourKey = cubeInfo.findAnyOf(cubeUse.keys)?.second!!
                cubeUse[colourKey] = cubeUse[colourKey]!! + numCubes
            }

            for (colour in fewestCubesNeeded) {
                if (colour.value < cubeUse[colour.key]!!)
                    fewestCubesNeeded[colour.key] = cubeUse[colour.key]!!
            }
        }

        return Pair(gameId, fewestCubesNeeded)
    }
}