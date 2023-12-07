package day07

import java.io.File

fun main() {
    when (System.getenv("part")) {
        "part2" -> println(solution2(readInput("input.txt")))
        "part1" -> println(solution1(readInput("input.txt")))
        else -> println(solution2(readInput("day07/input.txt")))
    }
}

fun solution1(lines: List<String>): Int {
    val rule = Solution1()
    return calculate(lines, rule)
}

fun solution2(lines: List<String>): Int {
    val rule = Solution2()
    return calculate(lines, rule)
}

private fun calculate(lines: List<String>, rule: Rule): Int {
    val hands = mutableMapOf<Int, MutableList<Hand>>()
    lines.map { line ->
        val (cards, bid) = line.split(" ")
        val hand = Hand(cards.map { Card.create(it, rule) }, bid.toInt())
        hands.getOrPut(rule.getType(hand)) { mutableListOf() }.add(hand)
    }

    return hands.keys.sorted()
        .flatMap { hands[it]!!.sortedBy { it.sum() } }
        .mapIndexed { index, hand -> (index + 1) * hand.bid }
        .sum()
}

data class Hand(val cards: List<Card>, val bid: Int) {

    fun sum(): Long {
        return cards.map { if (it.strength < 10) "0${it.strength}" else "${it.strength}" }.joinToString("")
            .toLong()
    }

}

data class Card(val strength: Int) {
    companion object {
        fun create(char: Char, rule: Rule) = Card(rule.getStrength(char))
    }
}

class Solution1 : Rule {
    override fun getType(hand: Hand): Int {
        val strengths = mutableMapOf<Int, Int>()
        hand.cards.forEach { card ->
            strengths[card.strength] = strengths.getOrDefault(card.strength, 0) + 1
        }

        return when (strengths.size) {
            1 -> 7
            2 -> if (strengths.containsValue(4)) 6 else 5
            3 -> if (strengths.containsValue(3)) 4 else 3
            4 -> 2
            else -> 1
        }
    }

    override fun getStrength(char: Char): Int {
        return when (char) {
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'J' -> 11
            'T' -> 10
            else -> char.digitToInt()
        }
    }
}

class Solution2 : Rule {
    override fun getType(hand: Hand): Int {
        val strengths = mutableMapOf<Int, Int>() // Card, Amount
        hand.cards.forEach { card ->
            strengths[card.strength] = strengths.getOrDefault(card.strength, 0) + 1
        }


        val jokerStrength = getStrength('J')
        val numberOfJokers = strengths[jokerStrength] ?: 0
        strengths.remove(jokerStrength)
        if (numberOfJokers > 0) {
            var best = Pair(0,0) // Card, Amount
            strengths.filter { it.key != jokerStrength}.forEach { if (it.value > best.second) best = it.toPair() }
            strengths[best.first] = best.second + numberOfJokers
        }

        return when (strengths.size) {
            1 -> 7
            2 -> if (strengths.containsValue(4)) 6 else 5
            3 -> if (strengths.containsValue(3)) 4 else 3
            4 -> 2
            else -> 1
        }
    }

    override fun getStrength(char: Char): Int {
        return when (char) {
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'J' -> 1
            'T' -> 10
            else -> char.digitToInt()
        }
    }
}

interface Rule {
    fun getType(hand: Hand): Int
    fun getStrength(char: Char): Int
}

fun readInput(path: String): List<String> = File(path).readLines()
