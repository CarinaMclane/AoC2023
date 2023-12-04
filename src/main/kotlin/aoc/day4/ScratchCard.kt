package aoc.day4

class ScratchCard(private val numbers: String) {

    val points: Int
        get()  { return calculatePoints() }

    val winningNums: Int
        get() {return calculateWinningNumbers()}

    private fun calculatePoints() : Int {
        var points = 0

        val winningNumbers = calculateWinningNumbers()

        if (winningNumbers == 0)
            return 0

        points = 1
        for (i in 2..winningNumbers) {
            points *= 2
        }
        return points
    }

    private fun calculateWinningNumbers(): Int {
        val splitNumbers = numbers.substring(numbers.indexOf(':')).split('|')
        val winningNums = splitNumbers[0].trim().split(' ').map{ it.toIntOrNull() }.filterNotNull().toSet()
        val cardNums = splitNumbers[1].trim().split(' ').map{ it.toIntOrNull() }.filterNotNull().toSet()

        val overlap = winningNums.intersect(cardNums)

        return overlap.size
    }
}