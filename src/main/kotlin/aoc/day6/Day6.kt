package aoc.day6

import aoc.day5.Almanac
import aoc.util.Helpers

class Day6 {
    val fileName = "src/main/kotlin/aoc/day6/input"
    val input = Helpers.readFile(fileName)

    fun solveTask1() : Long {
        val raceTimes = input?.get(0)?.split(' ')?.map{ it.toLongOrNull()}?.filterNotNull()
        val prevRecords = input?.get(1)?.split(' ')?.map{ it.toLongOrNull()}?.filterNotNull()

        var result = 1L
        for (i in raceTimes!!.indices) {
            result *= BoatRace(raceTimes[i]).findWaysOfBeatingRecord(prevRecords!![i])

        }

        return result
    }

    fun solveTask2() : Long {
        val raceTimes = input?.get(0)?.filter{ it != ' ' }?.split(':')?.map {it.toLongOrNull()}?.filterNotNull()
        val prevRecords = input?.get(1)?.filter { it != ' ' }?.split(':')?.map {it.toLongOrNull()}?.filterNotNull()

        var result = 1L
        for (i in raceTimes!!.indices) {
            result *= BoatRace(raceTimes[i]).findWaysOfBeatingRecord(prevRecords!![i])

        }
        return result
    }
}