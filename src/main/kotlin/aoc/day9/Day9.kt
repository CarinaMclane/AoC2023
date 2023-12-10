package aoc.day9

import aoc.util.Helpers

class Day9 {
    val fileName = "src/main/kotlin/aoc/day9/input"
    val input = Helpers.readFile(fileName)

    fun solveTask1() : Long {
        val sum = input?.sumOf {history ->
            calcLastValue(history)
        }
        return sum!!
    }

    fun solveTask2() : Long {
        val sum = input?.sumOf {history ->
            calcLastValue(history, true)
        }

        return sum!!
    }

    private fun calcLastValue(history: String, reverse: Boolean = false): Long {
        val regex = Regex("-?\\d+")
        val nums = regex.findAll(history).map{it.value.toLongOrNull()}.filterNotNull().toMutableList()
        val zipFunc : (Long, Long) -> Long = { a, b -> b - a }
        val listOfSeqs = mutableListOf(nums)
        listOfSeqs.add(nums.zipWithNext(zipFunc).toMutableList())
        while (listOfSeqs.last().any { it != 0L }) {
            listOfSeqs.add(listOfSeqs.last().zipWithNext(zipFunc).toMutableList())
        }

        listOfSeqs.last().add(0)

        if (reverse)
            listOfSeqs.forEach { it.reverse() }

        for (i in listOfSeqs.size-2 downTo 0)
        {
            if (!reverse)
                listOfSeqs[i].add(listOfSeqs[i].last() + listOfSeqs[i+1].last())
            else
                listOfSeqs[i].add(listOfSeqs[i].last() - listOfSeqs[i+1].last())

        }

        return listOfSeqs[0].last()
    }
}