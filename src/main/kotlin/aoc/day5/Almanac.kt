package aoc.day5

import kotlin.math.min

class Almanac(val mappings: List<String>, val useSeedRange: Boolean = false) {
    class SeedRange(val start: Long = 0,
                    val end: Long = 0) {

    }
    class Map(
        val name : String = "",
        val destRangeStart: Long = 0,
        val srcRangeStart: Long = 0,
        val rangeLength: Long = 0
    ) {
        fun convert(value: Long): Long {
            val srcRange = LongRange(srcRangeStart, srcRangeStart + rangeLength)
            return if (srcRange.contains(value))
                destRangeStart + value - srcRangeStart
            else
                value
        }

        companion object {
           fun fromStrings(name: String, input: String): Map {
               val tokens = input.trim().split(' ')
               return Map(name, tokens[0].toLong(),tokens[1].toLong(), tokens[2].toLong())
           }
       }
    }

    val mapsLists = ArrayList<ArrayList<Map>>()
    val seeds = ArrayList<Long>()
    val seedRanges = ArrayList<SeedRange>()
    init {
        val iterator = mappings.iterator()

        if (useSeedRange)
        {
            val seedRangeInput = iterator.next().substringAfter(':').trim().split(' ')
            val seedIterator = seedRangeInput.iterator()
            while (seedIterator.hasNext())
            {
                seedRanges.add(getSeedRange(seedIterator))
            }
        }
        else {
            seeds.addAll(iterator.next().substringAfter(':').trim().split(' ').mapNotNull { it.toLongOrNull() })
        }
        iterator.next()

        while (iterator.hasNext())
        {
            mapsLists.add(getMapsPerSection(iterator))
        }

        println("Map lists size ${mapsLists.size}")
    }



    fun getLocations() : List<Long> {
        val locations = ArrayList<Long>()
        seeds.forEach { seed ->
            var value = seed
            mapsLists.forEach{sectionMaps ->
                value = getMappedValue(value, sectionMaps)

            }
            locations.add(value)
        }

        return locations
    }

    fun getSmallestRangeLocation() : Long {
        var minLoc = Long.MAX_VALUE
        seedRanges.forEach {seedRange ->
            for (seed in seedRange.start..seedRange.end)
            {
                var value = seed
                mapsLists.forEach{ sectionMaps ->
                    value = getMappedValue(value, sectionMaps)
                }
                minLoc = min(minLoc, value)
            }
        }

        return minLoc
    }

    private fun getMappedValue(value: Long, sectionMaps: java.util.ArrayList<Map>): Long {
        sectionMaps.forEach{map ->
            val newValue = map.convert(value)
            if (newValue != value) {
                return newValue
            }
        }

        return value
    }

    private fun getSeedRange(iterator: Iterator<String>): SeedRange {
        val startRange = iterator.next().toLong()
        val endRange = startRange + iterator.next().toLong()
        return SeedRange(startRange, endRange-1)
    }

    private fun getMapsPerSection(iterator: Iterator<String>): ArrayList<Map> {
        // Title
        val name = iterator.next().substringBefore(':')
        println("Maps section: $name")
        val mapList = ArrayList<Map>()
        while(iterator.hasNext())
        {
            val nextString = iterator.next()
            if (nextString.length > 1)
                mapList.add(Map.fromStrings(name, nextString))
            else
                return mapList
        }

        return mapList
    }
}