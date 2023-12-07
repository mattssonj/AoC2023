package day06

import java.io.File

fun main() {
    when (System.getenv("part")) {
        "part2" -> println(solution2(readInput("input.txt")))
        "part1" -> println(solution1(readInput("input.txt")))
        else -> println(solution2(readInput("day06/input.txt")))
    }
}

fun solution1(lines: List<String>): Long {
    val (time, distance) = lines.map { it.split(":")[1].split(" ").mapNotNull { it.toLongOrNull() } }
    val timeToDistance = time.zip(distance)

    return timeToDistance.map { calculatePossibleWayToWin(it) }.fold(1) { acc, i -> acc * i }
}

fun solution2(lines: List<String>): Number {
    val (time, distance) = lines.map { it.split(":")[1].replace(" ", "").toLong() }
    return calculatePossibleWayToWin(time to distance)
}

private fun calculatePossibleWayToWin(it: Pair<Long, Long>): Long {
    val time = it.first
    val recordDistance = it.second
    val waysToBeatTheRecord = (0..time).filter { waiting ->
        val current = time - waiting
        val possibleDistance = current * waiting
        recordDistance < possibleDistance
    }.size
    return waysToBeatTheRecord.toLong()
}


fun readInput(path: String): List<String> = File(path).readLines()
