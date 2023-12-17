package aoc.day11

import kotlin.math.abs

class GalaxyMap(input: List<String>) {
    var map: List<String>
    var colDists : List<Char>
    var rowDists : List<Char>
    init {
        map = input

        val countsForEachIndex = (0..<map[0].length).map { index ->
            map.count { it[index] != '.' }
        }

        colDists = countsForEachIndex.map {count ->
            if (count > 0)
                ' '
            else
                '*'
        }

        rowDists = map.map { line ->
            if (line.count {it != '.'} > 0)
                ' '
            else
                '*'
        }
    }

    fun getGalaxyLocations() : List<Pair<Int,Int>> {
        var galaxyCounter = 0
        val locations = map.flatMapIndexed { i, line ->
            line.mapIndexedNotNull { j, char ->
                if (char == '#') {
                    Pair(i,j)
                }
                else null
            }
        }

        return locations
    }

    fun calcGalaxyDistances(galaxies: List<Pair<Int,Int>>, mult:Int = 2) : List<Long> {
        val dists = mutableListOf<Long>()
        for (i in galaxies.indices) {
            for (j in i+1..<galaxies.size) {
                val result = getDistance(galaxies[j], galaxies[i], mult)
                dists.add(result.toLong())
            }
        }
        return dists
    }

    private fun getDistance(a: Pair<Int, Int>, b: Pair<Int, Int>, mult: Int = 2): Int {
        val horDist = horizontalDist(a.second, b.second, mult)
        val vertDist = verticalDist(a.first, b.first, mult)
        return horDist + vertDist
    }

    private fun horizontalDist(a: Int, b: Int, mult: Int = 2): Int {
        val max = maxOf(a,b)
        val min = minOf(a,b)

        var dist = 0

        return colDists.filterIndexed{ idx, _ ->
            IntRange(min,max-1).contains(idx)
        }.sumOf {if (it == '*') mult else 1}
    }

    private fun verticalDist(a: Int, b: Int, mult: Int = 2): Int {
        val max = maxOf(a,b)
        val min = minOf(a,b)
        return rowDists.filterIndexed{idx, _ ->
            IntRange(min,max-1).contains(idx)
        }.sumOf { if (it == '*') mult else 1 }
    }

}