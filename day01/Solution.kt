package day1

import java.io.File

fun main() {
    when (System.getenv("part")) {
        "part2" -> println(solution2(readInput("input.txt")))
        "part1" -> println(solution1(readInput("input.txt")))
        else -> println(solution2(readInput("day01/input.txt")))
    }
}

fun solution1(lines: List<String>): Int {
    return lines.sumOf {
        val first = it.first { it.isDigit() }
        val last = it.last { it.isDigit() }
        "$first$last".toInt()
    }
}

fun solution2(lines: List<String>): Int {
    return lines
        .map { it.allNumbers() }
        .sumOf { "${it.first()}${it.last()}".toInt() }
}

enum class DigitAsText(val digit: Int) {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9);

    companion object {
        fun startsWithDigit(s: String): DigitAsText? =
            entries.firstOrNull { s.startsWith(it.name.lowercase()) }
    }
}

private fun String.allNumbers(): List<Int> {
    val numbers = mutableListOf<Int?>()
    indices.forEach {
        if (this[it].isDigit()) numbers.add(this[it].digitToInt())
        else numbers.add(DigitAsText.startsWithDigit(this.substring(it))?.digit)
    }
    return numbers.filterNotNull()
}

fun readInput(path: String): List<String> = File(path).readLines()
