package day04

import java.io.File

fun main() {
    when (System.getenv("part")) {
        "part2" -> println(solution2(readInput("input.txt")))
        "part1" -> println(solution1(readInput("input.txt")))
        else -> println(solution2(readInput("day04/input.txt")))
    }
}

fun solution1(lines: List<String>): Int {
    return lines.sumOf {
        val (winning, mine) = it.split(":")[1].split("|")
            .map { it.split(" ").mapNotNull { it.toIntOrNull() }.toSet() }
        val sum = mine.fold(0) { current, number ->
            if (winning.contains(number)) {
                if (current == 0) 1 else current * 2
            } else current
        }
        sum
    }
}

fun solution2(lines: List<String>): Number {
    val cards = mutableMapOf<Int, Int>()

    lines.forEach { line ->
        val (winning, mine) = line.split(":")[1].split("|")
            .map { it.split(" ").mapNotNull { it.toIntOrNull() }.toSet() }

        val cardNumber = line.split(":")[0].split(" ").last().toInt()
        val copysToWin = mine.filter { winning.contains(it) }.size

        cards[cardNumber] = cards.getOrPut(cardNumber) { 0 } + 1

        (cardNumber + 1..cardNumber + copysToWin).forEach { cards[it] = cards.getOrPut(it) { 0 } + cards[cardNumber]!! }
    }

    return cards.values.sum()
}


fun readInput(path: String): List<String> = File(path).readLines()
