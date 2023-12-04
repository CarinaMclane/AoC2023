package aoc.day4

class ScratchCardGame(private val scratchCards: List<ScratchCard>) {
    private val wonCards = MutableList(scratchCards.size) {0}

    fun scratchAllCards() : List<Int> {
        for (i in scratchCards.indices)
        {
            val instances = wonCards[i]+1
            val numWinsOnThisCard = scratchCards[i].winningNums
            addWonCards(i,numWinsOnThisCard, instances)
        }

        return wonCards.map { it + 1 }
    }

    private fun addWonCards(gameIdx: Int, gamesWon: Int, instances: Int) {
        for (i in 1..gamesWon)
        {
            if (gameIdx+i < wonCards.size)
                wonCards[gameIdx+i] += instances
        }
    }
}