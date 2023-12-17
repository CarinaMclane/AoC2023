package aoc.day12

class SpringMap(input: List<String>, mult: Int = 1) {
    val map: MutableList<Pair<String, String>>

    init {
        map = input.map {
            val (left, right) = it.split(" ", limit = 2)
            ("$left?").repeat(mult).dropLast(1) to ("$right,").repeat(mult).dropLast(1)
        }.toMutableList()
    }

    fun getPossCombinations(): Long {
        var combos = 0L
        map.forEach{pair ->
            val groups = pair.second.split(',').mapNotNull { it.toIntOrNull() }
            val numMatches = findCombinations(pair.first, groups)
            combos += numMatches
        }

        return combos
    }

    private fun findCombinations(record: String, damageGroups: List<Int>, cache : MutableMap<Pair<String, List<Int>>, Long> = hashMapOf()) : Long {
        val key = record to damageGroups
        cache[key]?.let {return it}


        if (record.isEmpty()) {
            return if (damageGroups.isEmpty()) 1 else 0
        }

        var result = 0L
        val recordIt = record.iterator()
        when (recordIt.next()) {
            '.' -> result = findCombinations(record.drop(1), damageGroups, cache)
            '?' -> {
                result = findCombinations(record.drop(1), damageGroups, cache) +
                         findCombinations("#${record.drop(1)}", damageGroups, cache)
            }
            '#' -> {
                // No more damages expected
                if (damageGroups.isEmpty())
                    return 0L

                val damageGroupCount = damageGroups[0]

                // Not enough record left to fulfill damage count
                if (record.length < damageGroupCount)
                    return 0L

                var thisCount = 1

                // attempt to follow the expected damage group count
                while(thisCount < damageGroupCount)
                {
                    when(recordIt.next()) {
                        '#', '?' -> {
                            thisCount++
                        }
                        '.' -> return 0L         // were expecting a longer damage
                    }
                }

                // Should have reached end of damage
                if (recordIt.hasNext())
                {
                    when(recordIt.next()) {
                        '.', '?' -> result = findCombinations(record.drop(damageGroupCount+1), damageGroups.drop(1), cache)
                        '#' -> return 0L
                    }
                } else {
                    // reached end of string, but continue recursion to cache and hit termination crtieria
                    result = findCombinations(record.drop(damageGroupCount), damageGroups.drop(1), cache)
                }

            }
        }

        cache[key] = result

        return result
    }

    // ended up not using, but may be useful at some other point
    private fun findMatches(size: Int, string: String) : List<IntRange> {
        val regexPattern = "(?=([$?#]{$size}))".toRegex()

        val groupedResults = regexPattern.findAll(string).mapNotNull {it.groups[1]?.range }
        return groupedResults.toList()
    }
}