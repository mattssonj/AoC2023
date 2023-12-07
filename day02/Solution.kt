package day02

import java.io.File

fun main() {
    when (System.getenv("part")) {
        "part2" -> println(solution2(readInput("input.txt")))
        "part1" -> println(solution1(readInput("input.txt")))
        else -> println(solution2(readInput("day02/input.txt")))
    }
}

fun solution1(lines: List<String>): Int {
    // only 12 red cubes, 13 green cubes, and 14 blue cubes
    val bag = mapOf(Color.RED to 12, Color.GREEN to 13, Color.BLUE to 14)
    return lines.map { Game(it, bag) }.filter { !it.isPossible() }.sumOf { it.gameNumber }
}

fun solution2(lines: List<String>): Number {
    return lines.map { Game(it, emptyMap()) }.sumOf { it.findThePower() }
}

class Game(line: String, bagOfCubes: Map<Color, Int>) {

    private val bag: Map<Color, Int>
    private val rounds: List<Round>
    val gameNumber: Int

    init {
        this.bag = bagOfCubes
        val (game, rounds) = line.split(":").map { it.trim() }
        this.gameNumber = game.split(" ")[1].toInt()
        this.rounds = rounds.split(";").map { it.trim() }.map { Round(it) }
    }

    fun findThePower(): Long {
        val lowestAmountUsedInRounds = mutableMapOf<Color, Int>()
        rounds.forEach {
            it.cubes.forEach {
                if ((lowestAmountUsedInRounds[it.color] ?: 0) < it.numberOfCubes) {
                    lowestAmountUsedInRounds[it.color] = it.numberOfCubes
                }
            }
        }
        return lowestAmountUsedInRounds.values.fold(1) { current: Int, i: Int -> current * i }.toLong()
    }

    fun isPossible(): Boolean {
        return rounds.any { it.cubes.any { isNotPossible(it) } }
    }

    private fun isNotPossible(cubes: Cubes): Boolean {
        val amountInBag = bag[cubes.color]!!
        return amountInBag < cubes.numberOfCubes
    }

}

class Round(s: String) {

    val cubes: List<Cubes>

    init {
        cubes = s.split(",").map { it.trim() }.map { it.toCubes() }
    }

    private fun String.toCubes(): Cubes {
        val (amount, color) = this.split(" ")
        return Cubes(amount.toInt(), Color.valueOf(color.uppercase()))
    }
}

data class Cubes(val numberOfCubes: Int, val color: Color)

enum class Color {
    RED,
    BLUE,
    GREEN
}

fun readInput(path: String): List<String> = File(path).readLines()
