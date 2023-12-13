package aoc.day11

import kotlin.math.abs

class GalaxyMap(input: List<String>) {
    var map: MutableList<String>
    var colDists : List<Int>
    var lineDists : List<Int>
    init {
        val tempMap = input.flatMap { line ->
            if (line.trim().all {it == '.'}) {
                listOf(line,line)
            } else {
                listOf(line)
            }
        }

        val countsForEachIndex = (0..<tempMap[0].length).map { index ->
            tempMap.count { it[index] != '.' }
        }

        tempMap.forEach { println(it) }

        map = mutableListOf<String>()
        tempMap.forEach {line ->
            val sb = StringBuilder()
            line.forEachIndexed{idx, char ->
                sb.append(char)
                if (countsForEachIndex[idx] == 0)
                    sb.append('.')
            }
            map.add(sb.toString())
        }

        println("Num galaxies per column:")
        println(countsForEachIndex)

        map.forEach { println(it) }

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

    fun calcGalaxyDistances(galaxies: List<Pair<Int,Int>>) : List<Int> {

        val dists = mutableListOf<Int>()
        for (i in galaxies.indices) {
            for (j in i+1..<galaxies.size) {
                val result = Pair(abs(galaxies[j].first - galaxies[i].first),abs(galaxies[j].second - galaxies[i].second))
                dists.add(result.first + result.second)
                println("${i+1} to ${j+1}: ${result.first + result.second} ")
            }
        }
        return dists
    }

}