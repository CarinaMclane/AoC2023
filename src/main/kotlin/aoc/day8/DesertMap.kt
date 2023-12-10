package aoc.day8

class DesertMap(input: List<String>) {
    var map = mutableMapOf<String, Pair<String, String>>()
    init {
        val regex = """(\w+)\s*=\s*\(\s*(\w+)\s*,\s*(\w+)\s*\)""".toRegex()

        input.forEach {
            val matchResult = regex.find(it)
            if (matchResult != null)
            {
                val (a,b,c) = matchResult.destructured
                map[a] = Pair(b,c)
            }
        }
    }

    fun findSteps(sequence: String, startPoint: String = "AAA", targetMatcher: (String)->Boolean =  { str: String -> str == "ZZZ" }) : Int {
        var seqIdx = 0
        var steps = 0

        var currentElement = startPoint
        while (!targetMatcher(currentElement)) {
            steps++
            val nextElement = if (sequence[seqIdx++ % sequence.length] == 'L') {
                map[currentElement]?.first
            } else {
                map[currentElement]?.second
            }

            currentElement = nextElement!!
        }

        return steps
    }

    fun findStepsPerPath(sequence:String): List<Long> {
        val currentElements = findAllStartElements()

        return currentElements.map {
            findSteps(sequence, it, {str: String -> str.endsWith('Z')}).toLong()
        }
    }




    fun calcNumStepsConcurrent(sequence: String) : Long {
        val nums = findStepsPerPath(sequence)

        val result = lcm(nums)
        return result
    }


    // General solution very long time to solve for input data.
    // Verified that all paths from xxA to xxZ produce perfect loops, so can use LCM
    fun findStepsConcurrent(sequence: String) : Long {
        var seqIdx = 0L
        var steps = 0L

        var currentElements = findAllStartElements()

        while (!allAtTargetElements(currentElements)) {
            steps++
            val nextElements = mutableSetOf<String>()
            currentElements.forEach { currentElement ->
                val nextElement = if (sequence[(seqIdx % sequence.length).toInt()] == 'L') {
                    map[currentElement]?.first
                } else {
                    map[currentElement]?.second
                }
                nextElements.add(nextElement!!)
            }
            seqIdx++
            currentElements = nextElements
            if (steps % 10000000 == 0L)
                println("Num steps: $steps")
        }

        return steps
    }

    fun findAllStartElements() : Set<String> {
        return map.filter {it.key[2] == 'A'}.keys
    }

    private fun allAtTargetElements(currentElements: Set<String>): Boolean {
        return currentElements.none { it[2] != 'Z' }
    }

    private fun gcd(x: Long, y: Long) : Long {
        var a = x
        var b = y
        while (b > 0)
        {
            val temp = b
            b = a % b
            a = temp
        }

        return a
    }

    private fun lcm(a: Long, b: Long) : Long {
        return a * (b / gcd(a,b))
    }

    private fun lcm(nums: List<Long>) : Long {
        val it = nums.iterator()
        var result = nums.iterator().next()
        while (it.hasNext())
            result = lcm(result, it.next())

        return result
    }

}