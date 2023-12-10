package aoc.day8

import aoc.util.Helpers

class Day8 {
    val fileName = "src/main/kotlin/aoc/day8/input"
    val input = Helpers.readFile(fileName)
    val moveSequence = input?.get(0)
    val desertMap = DesertMap(input?.subList(2,input.size)!!)


    fun solveTask1() : Int {
        //val result = desertMap.findSteps(moveSequence!!)
        //return result.first

        return 0
    }

    fun solveTask2() : Long {
        val result = desertMap.calcNumStepsConcurrent(moveSequence!!)
            //desertMap.findStepsConcurrent(moveSequence!!)
        return result
    }

    fun solveTask2Alt() : Long {
        val result = desertMap.findStepsConcurrent(moveSequence!!)
        return result
    }
}