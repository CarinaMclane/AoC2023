package aoc.day10

class PipeSystem(val input: MutableList<String>) {
    var charMap = input.map{it.toMutableList()}.toMutableList()

    var loopCoords = mutableListOf<Pair<Int,Int>>()
    val dirMap = mapOf(
        '|' to "NS",
        '-' to "EW",
        'L' to "NE",
        'J' to "NW",
        '7' to "SW",
        'F' to "SE",
        'S' to "NSEW"
    )


    init {
        var indexOfS: Pair<Int,Int>? = null

        for (y in input.indices) {
            for (x in input[y].indices) {
                if (getChar(x,y) == 'S') {
                    indexOfS = Pair(x,y)
                }
            }
        }

        loopCoords.add(indexOfS!!)

        var currentIdx = indexOfS!!
        var nextPipe = findConnectingPipe(currentIdx)
        while ( nextPipe != null) {
            loopCoords.add(nextPipe)
            nextPipe = findConnectingPipe(nextPipe)
        }
    }

    private fun getChar(x: Int, y: Int) : Char {
        if (inBounds(x,y))
            return input[y][x]
        else
            return '.'
    }

    private fun inBounds(x: Int, y: Int): Boolean {
        return  x >= 0 &&
                y >= 0 &&
                x < input[0].length &&
                y < input.size
    }



    private fun findConnectingPipe(currentIdx: Pair<Int, Int>): Pair<Int,Int>? {
        val currentDirs = dirMap[input[currentIdx.second][currentIdx.first]]
        assert(currentDirs != null)

        if(currentDirs?.contains('S') == true) {
            val nextIdx = Pair(currentIdx.first, currentIdx.second+1)
            val hasMatchingPipe = dirMap[getChar(nextIdx.first, nextIdx.second)]?.contains('N') == true
            if (!loopCoords.contains(nextIdx) && hasMatchingPipe)
                return nextIdx
        }


        if (currentDirs?.contains('W') == true) {
            val nextIdx = Pair(currentIdx.first-1, currentIdx.second)
            val hasMatchingPipe = dirMap[getChar(nextIdx.first, nextIdx.second)]?.contains('E') == true
            if (!loopCoords.contains(nextIdx) && hasMatchingPipe)
                return nextIdx
        }


        if(currentDirs?.contains('N') == true) {
            val nextIdx = Pair(currentIdx.first, currentIdx.second-1)
            val hasMatchingPipe = dirMap[getChar(nextIdx.first, nextIdx.second)]?.contains('S') == true
            if (!loopCoords.contains(nextIdx) && hasMatchingPipe)
                return nextIdx
        }

        if (currentDirs?.contains('E') == true) {
            val nextIdx = Pair(currentIdx.first+1, currentIdx.second)
            val hasMatchingPipe = dirMap[getChar(nextIdx.first, nextIdx.second)]?.contains('W') == true
            if (!loopCoords.contains(nextIdx) && hasMatchingPipe)
                return nextIdx
        }

        return null
    }

    fun getFurthestPoint() : Int {
        return loopCoords.size/2
    }

    fun fillInsideSpacesNew() : Int {
        val dir = determineDirection(loopCoords)

        // ensure clockwise
        if (dir > 0) loopCoords.reverse()

        println("Loop: $loopCoords")
        println("Clockwise (should be -1): ${determineDirection(loopCoords)}")

        val it = loopCoords.iterator()
        var prev = it.next()
        var filledSpaces = 0

        clearMap()
        println("Map:")
        charMap.forEach { println(it) }

        while(it.hasNext()) {
            val current = it.next()
            val dirX = current.first - prev.first
            val dirY = current.second - prev.second

            val currChar = getChar(current.first, current.second)

            if (dirX < 0) {
                if (fillSpace(current.first, current.second - 1)) filledSpaces++
                if (currChar == 'F') {
                    if (fillSpace(current.first-1, current.second-1)) filledSpaces++
                }
            }
            if (dirX > 0) {
                if (fillSpace(current.first, current.second + 1)) filledSpaces++
                if (currChar == 'J') {
                    if (fillSpace(current.first+1, current.second+1)) filledSpaces++
                }
            }

            if (dirY > 0) {
                if (fillSpace(current.first - 1, current.second)) filledSpaces++
                if (currChar == 'L') {
                    if(fillSpace(current.first-1, current.second+1)) filledSpaces++
                }
            }
            if (dirY < 0) {
                if (fillSpace(current.first+1, current.second)) filledSpaces++
                if (currChar == '7') {
                    if (fillSpace(current.first+1, current.second-1)) filledSpaces++
                }
            }



            prev = current
        }

        println("Filled map:")
        charMap.forEach { println(it) }

        var newFilledSpaces = growFilledAreas()
        while (newFilledSpaces != 0)
        {
            filledSpaces += newFilledSpaces
            newFilledSpaces = growFilledAreas()
        }

        println("Grown map:")
        charMap.forEach { println(it) }

        val sum = charMap.sumOf{line -> line.count{it == 'x'}}
        return sum
    }

    private fun orientation(p: Pair<Int,Int>, q: Pair<Int,Int>,r: Pair<Int,Int>) : Int {
        val cx = (q.second - p.second) * (r.first - q.first) - (q.first - p.first)*(r.second - q.second)

        return when {
            cx == 0 -> 0
            cx > 0 -> 1
            else -> -1
        }

    }
    private fun determineDirection(loopCoords: MutableList<Pair<Int, Int>>): Int {
        var sumOrientation = 0
        for (i in loopCoords.indices) {
            sumOrientation += orientation(
                loopCoords[i],
                loopCoords[(i+1) % loopCoords.size],
                loopCoords[(i+2) % loopCoords.size])
        }

        return if (sumOrientation > 0) { 1 } else { -1 }
    }

    private fun clearMap() {
        for (y in charMap.indices) {
            for (x in charMap[y].indices) {
                if (loopCoords.none{it.first == x && it.second == y}) {
                    charMap[y][x] = '.'
                }
            }
        }
    }



    private fun growFilledAreas() : Int {
        var filledSpaces = 0
        for (y in charMap.indices) {
            for (x in charMap[y].indices) {
                if (charMap[y][x] == 'x') {
                    if(fillSpace(x,y+1))filledSpaces++
                    if(fillSpace(x,y-1))filledSpaces++
                    if(fillSpace(x+1,y))filledSpaces++
                    if(fillSpace(x-1,y))filledSpaces++
                    if(fillSpace(x+1,y+1))filledSpaces++
                    if(fillSpace(x+1,y-1))filledSpaces++
                    if(fillSpace(x-1,y-1))filledSpaces++
                    if(fillSpace(x-1,y+1))filledSpaces++
                }
            }
        }

        return filledSpaces
    }

    private fun fillSpace(x: Int, y: Int) : Boolean {
        if (!inBounds(x,y))
            return false

        if (loopCoords.none{it.first == x && it.second == y} &&
            charMap[y][x] != 'x') {
            charMap[y][x] = 'x'
            return true
        }

        return false
    }
}