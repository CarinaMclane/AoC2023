package aoc.day13

import aoc.util.Helpers

class Day13 {
    val fileName = "src/main/kotlin/aoc/day13/example_input"
    val input = Helpers.readFile(fileName)

    private fun splitByEmptyLine(input: List<String>): List<List<String>> {
        val result = mutableListOf<MutableList<String>>()
        result.add(mutableListOf<String>())
        var idx = 0
        input.forEach { line ->
            if (line == "")
            {
                result.add(mutableListOf())
                idx++
            }
            else {
                result[idx].add(line)
            }
        }

        return result
    }

    fun solveTask1() : Int {
        val separatedInputs = splitByEmptyLine(input!!)

        var sum = 0
        separatedInputs.forEach {pattern ->
            println("Original: ")
            pattern.forEach { println(it) }
            findVerticalReflection(pattern).forEach {
                sum += it
            }

            println("Inverted: ")
            val rotated = rotateMatrix(pattern)
            rotated.forEach { println(it) }
            findVerticalReflection(rotated).forEach {
                sum += (it * 100)
            }
        }
        return sum
    }

    fun solveTask2() : Int {
        val separatedInputs = splitByEmptyLine(input!!)

        var verticalCandidate : Pair<Int,Int>? = null
        var horizontalCandidate : Pair<Int,Int>? = null
        // Step 1, find the differences
        separatedInputs.forEach {pattern ->
            println("Original: ")
            pattern.forEach { println(it) }
            val diffCandidates = mapVerticalDiff(pattern)
            diffCandidates.forEachIndexed {reflIdx, diffs ->
                println("Diffs for idx $reflIdx is $diffs")
            }
            diffCandidates.forEachIndexed {idx, lines ->
                if (lines.size == 1)
                    verticalCandidate = Pair(idx, lines[0])
            }


            println("Inverted: ")
            val rotated = rotateMatrix(pattern)
            rotated.forEach { println(it) }
            val rotDiffCandidates = mapVerticalDiff(rotated)
            rotDiffCandidates.forEachIndexed {reflIdx, diffs ->
                println("Diffs for idx $reflIdx is $diffs")
            }
            rotDiffCandidates.forEachIndexed {idx, lines ->
                if (lines.size == 1)
                    horizontalCandidate = Pair(idx, lines[0])
            }
        }

        return 0
    }

    private fun mapVerticalDiff(input: List<String>) : List<List<Int>> {
        val returnList = mutableListOf(mutableListOf<Int>())
        val size = input[0].length

        for (splitIdx in 1..<size) {
            val listToCheck = input.map { str ->
                val left = str.substring(0, splitIdx)
                val right = str.substring(splitIdx)
                Pair(left.reversed(), right)
            }

            val listOfDiffs = mutableListOf<Int>()
            listToCheck.forEachIndexed() {lineIdx, pair ->
                val leftIt = pair.first.iterator()
                val rightIt = pair.second.iterator()
                while (leftIt.hasNext() && rightIt.hasNext())
                {
                    if (leftIt.next() != rightIt.next())
                        listOfDiffs.add(lineIdx)
                }
            }
            returnList.add(listOfDiffs)
        }

        return returnList
    }
    private fun findVerticalReflection(input: List<String>) : List<Int> {

        val size = input[0].length
        val returnList = mutableListOf<Int>()
        for (splitIdx in 1..<size) {
            val listToCheck = input.map { str ->
                val left = str.substring(0, splitIdx)
                val right = str.substring(splitIdx)
                Pair(left.reversed(), right)
            }

            var isRefl = true
            listToCheck.forEach {
                val leftIt = it.first.iterator()
                val rightIt = it.second.iterator()
                while (leftIt.hasNext() && rightIt.hasNext())
                {
                    if (leftIt.next() != rightIt.next())
                        isRefl = false
                }
            }

            if (isRefl)
            {
                returnList.add(splitIdx)
                println("Found reflection at: $splitIdx")
            }
        }

        return returnList
    }

    fun rotateMatrix(origin: List<String>): List<String> {
        val result = MutableList<String>(origin[0].length) {""}
        origin.forEachIndexed() {rowIdx, row ->
            row.forEachIndexed {colIdx, col ->
                result[colIdx] = "${result[colIdx]}${row[colIdx]}"
            }
        }
        return result
    }
}