package aoc.day3

class Schematic(private val schema: List<String>) {
    fun getPartNumbers() : List<Int> {
        val result = ArrayList<Int>()

        for (i in schema.indices) {
            result.addAll(getPartNumbersForLine(i))
        }

        return result
    }

    fun getGearRatios() : Collection<Collection<Int>> {
        val result = ArrayList<Collection<Int>>()

        for (i in schema.indices) {
            result.addAll(getGearRatiosForLine(i))
        }
        return result
    }

    private fun getGearRatiosForLine(lineIdx: Int): Collection<Collection<Int>>{
        val asteriskRegex = Regex("\\*")
        val asterisks = asteriskRegex.findAll(schema[lineIdx])
        val gearRatios = ArrayList<ArrayList<Int>>()

        asterisks.forEach {asterisk ->
            val adjacentNumbers = ArrayList<Int>()
            val index = asterisk.range.first

            adjacentNumbers.addAll(getAdjacentNumbersOnLine(lineIdx-1, index))
            adjacentNumbers.addAll(getAdjacentNumbersOnLine(lineIdx, index))
            adjacentNumbers.addAll(getAdjacentNumbersOnLine(lineIdx+1, index))

            if (adjacentNumbers.size > 1)
                gearRatios.add(adjacentNumbers)
        }

        return gearRatios
    }

    private fun getAdjacentNumbersOnLine(lineIdx: Int, index: Int): Collection<Int> {
        val numberRegex = Regex("\\d+")
        val numbers = numberRegex.findAll(schema[lineIdx])
        val adjacentNumbers = arrayListOf<Int>()

        numbers.forEach {number ->
            var extendedRange = IntRange(number.range.first-1, number.range.last+1)
            if (extendedRange.contains(index))
                adjacentNumbers.add(number.value.toInt())
        }

        return adjacentNumbers
    }

    private fun getPartNumbersForLine(lineIdx: Int): ArrayList<Int> {
        // find number
        val regex = Regex("\\d+")
        val numbers = regex.findAll(schema[lineIdx])
        val partNumbers = ArrayList<Int>()
        for (number in numbers) {
            val startIdx = number.range.first
            val endIdx = number.range.last

            if (hasSymbol(lineIdx-1, startIdx-1, endIdx+1) ||
                hasSymbol(lineIdx, startIdx-1, endIdx+1) ||
                hasSymbol(lineIdx+1, startIdx-1, endIdx+1)) {
                partNumbers.add(number.value.toInt())
            }
        }

        return partNumbers
    }

    private fun hasSymbol(lineIdx: Int, startIdx: Int, endIdx: Int): Boolean {
        val regex = Regex("[^\\d.]")

        if (lineIdx < 0 || lineIdx > schema.size - 1)
            return false

        val substring = schema[lineIdx].substring(
            startIdx.coerceAtLeast(0),
            endIdx.coerceAtMost(schema[lineIdx].length - 1) + 1
        )

        return regex.find(substring) != null
    }
}