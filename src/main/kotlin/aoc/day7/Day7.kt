package aoc.day7

import aoc.util.Helpers

class Day7 {
    val fileName = "src/main/kotlin/aoc/day7/input"
    val input = Helpers.readFile(fileName)

    fun solveTask1() : Int {
        val hands = input?.map { line -> Hand(line) }?.sortedByDescending { it.getScore() }
        val reSortedHands = hands?.sortedWith(Hand.valueComparator)

        var index = 1
        var value = 0
        reSortedHands?.forEach {hand -> value += (hand.bid * index++) }

        return value
    }

    fun solveTask2() : Long {
        val hands = input?.map { Hand(it) }?.sortedByDescending { it.getScore(true) }
        val reSortedHands = hands?.sortedWith(Hand.valueComparatorWithJoker)

        var index = 1
        var value : Long = 0
        reSortedHands?.forEach {hand -> value += ((hand.bid * index++).toLong()) }

        return value
    }
}