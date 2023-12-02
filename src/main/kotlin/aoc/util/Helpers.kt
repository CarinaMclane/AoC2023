package aoc.util

import java.io.File
import java.lang.Exception

object Helpers {
    public fun readFile(fileName:String) : List<String>? {
        try {
            val file = File(fileName)
            return file.readLines()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}