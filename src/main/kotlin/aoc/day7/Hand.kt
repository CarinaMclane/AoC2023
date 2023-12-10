package aoc.day7

class Hand(input: String) {
    private val cards = arrayListOf<Char>()
    var bid = 0
    init {
        val splitString = input.split(' ')
        splitString[0].toCharArray().toCollection(cards)
        bid = splitString[1].toInt()
    }

    fun getScore(useJoker: Boolean = false): Int {
        var sortedMap = cards.groupingBy{it}.eachCount().toList().sortedByDescending{it.second}.toMutableList()

        // replace J
        if (useJoker)
            adjustHand(sortedMap)

        when (sortedMap[0].second) {
            5 -> return 7
            4 -> return 6
            3 -> {
                return if (sortedMap[1].second == 2)
                    5
                else
                    4
            }
            2 -> {
                return if (sortedMap[1].second == 2)
                    3
                else
                    2
            }
            else -> {
                return 1
            }
        }
    }

    private fun adjustHand(sortedMap: MutableList<Pair<Char, Int>>) {
        val numJokers = cards.count{it == 'J'}

        if (numJokers == 5)
            return

        sortedMap.removeIf {it.first == 'J'}
        sortedMap[0] = Pair(sortedMap[0].first, sortedMap[0].second + numJokers)
    }

    @Override
    override fun toString() : String {
        return "Hand: $cards with bid $bid has score: ${getScore(false)} (${getScore(true)})"
    }

    fun isMoreValuableThan(otherHand: Hand, useJoker: Boolean) : Int {
        val cardValues = if (!useJoker)
            listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
        else
            listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')

        val myScore = getScore(useJoker)
        val otherScore = otherHand.getScore(useJoker)
        if (myScore > otherScore)
            return 1
        if (otherScore > myScore)
            return -1

        val thisIt = cards.iterator()
        val otherIt = otherHand.cards.iterator()

        while (thisIt.hasNext())
        {
            val thisIdx = cardValues.indexOf(thisIt.next())
            val otherIdx = cardValues.indexOf(otherIt.next())

            if (thisIdx < otherIdx)
                return 1
            if (otherIdx < thisIdx)
                return -1
        }

        return 0
    }

    companion object {
        val valueComparator = Comparator<Hand> { o1, o2 -> (o1?.isMoreValuableThan(o2!!, false)) ?: 0 }
        val valueComparatorWithJoker = Comparator<Hand> { o1, o2 -> (o1?.isMoreValuableThan(o2!!, true)) ?: 0 }
    }
}