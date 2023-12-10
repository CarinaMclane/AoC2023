package aoc.day6

import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

class BoatRace(val raceTime: Long) {
    fun findWaysOfBeatingRecord(prevRecord: Long): Long {
        val matchTime = solveQuadraticEquation(-1, raceTime, -prevRecord)

        // nudge off potential exact matching of records
        val minVal = ceil(matchTime.first!! + 0.01).toLong()
        val maxVal = floor(matchTime.second!! -0.01).toLong()

        return (maxVal - minVal) + 1
    }

    private fun solveQuadraticEquation(a: Long, b: Long, c: Long): Pair<Double?,Double?> {
        val discriminant = b * b - (4 * a * c)
        if (discriminant < 0)
            return Pair(null, null)

        val sqrtDisc = sqrt(discriminant.toDouble())
        val root1 = (-b + sqrtDisc) / (2 * a)
        val root2 = (-b - sqrtDisc) / (2 * a)

        return Pair(root1, root2)
    }
}