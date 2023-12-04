package aoc.day4

import aoc.util.Helpers

class Day4 {
    val fileName = "src/main/kotlin/aoc/day4/input"
    val input = Helpers.readFile(fileName)
    
    fun solveTask1() : Int {
        var sum = 0
        input?.forEach { line ->
            val scratchCard = ScratchCard(line)
            sum += scratchCard.points
        }

        return sum
    }

    fun solveTask2() : Int {
        val cards = input?.map {ScratchCard(it)}
        val cardGame = ScratchCardGame(cards ?: listOf())
        return cardGame.scratchAllCards().sum()
    }
}