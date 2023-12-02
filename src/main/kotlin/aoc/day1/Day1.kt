package aoc.day1

import aoc.util.Helpers
import java.io.File
import java.lang.Exception

class Day1 {
    val wordList = listOf("one","two","three","four","five","six","seven","eight","nine")

    fun solveTask1() : Int {
        val fileName = "src/main/kotlin/aoc/day1/input"
        var sum = 0

        val input = Helpers.readFile(fileName)

        for (line in input ?: listOf()) {
            sum += extractNumber(line) ?: 0
        }

        return sum
    }

    fun solveTask2() : Int {
        val fileName = "src/main/kotlin/aoc/day1/input"
        var sum = 0

        val input = Helpers.readFile(fileName)
        for (line in input ?: listOf()) {
            sum += extractDigitOrWordValue(line) ?: 0
        }

        return sum
    }

    private fun extractNumber(input: String): Int? {
        val regex = Regex("\\d")
        val allMatches = regex.findAll(input)
        val firstDigit = allMatches.firstOrNull()?.value?.toIntOrNull()
        val lastDigit = allMatches.lastOrNull()?.value?.toIntOrNull()

        return "$firstDigit$lastDigit".toIntOrNull()
    }

    private fun extractDigitOrWordValue(input: String): Int? {
        val digitRegex = Regex("\\d")
        val allDigitMatches = digitRegex.findAll(input)

        val firstWordMatch = input.findAnyOf(wordList)
        val lastWordMatch = input.findLastAnyOf(wordList)

        val firstWordIndex = firstWordMatch?.first ?: Integer.MAX_VALUE
        val firstDigitIndex = if (allDigitMatches.any())
        {
            allDigitMatches.first().range.first
        } else {
            Integer.MAX_VALUE
        }


        val firstDigit = if (firstDigitIndex < firstWordIndex)
        {
            allDigitMatches.first().value
        } else {
            wordList.indexOf(firstWordMatch?.second)+1
        }

        val lastWordIndex = lastWordMatch?.first ?: -1
        val lastDigitIndex = if (allDigitMatches.any()) {
            allDigitMatches.last().range.first
        } else {
            -1
        }

        val lastDigit = if (lastDigitIndex > lastWordIndex)
        {
            allDigitMatches.last().value
        } else {
            wordList.indexOf(lastWordMatch?.second)+1
        }

        return "$firstDigit$lastDigit".toIntOrNull()
    }
}
