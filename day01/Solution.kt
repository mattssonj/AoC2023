package day1
import java.io.File

fun main() {
    when (System.getenv("part")) {
        "part2" -> println(solution2(readInput("input.txt")))
        "part1" -> println(solution1(readInput("input.txt")))
        else -> println(solution1(readInput("day01/test.txt")))
    }
}

fun solution1(lines: List<String>): Int {
    println("Testing 1")
    return 1
}

fun solution2(lines: List<String>): Int {
    println("Testing 2")
    return 2
}

fun readInput(path: String): List<String> = File(path).readLines()
