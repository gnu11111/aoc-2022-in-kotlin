package at.gnu.adventofcode.year2022

import at.gnu.adventofcode.year2022.Day02.Sign.*

class Day02(input: List<String>) {

    companion object {
        const val input = "/adventofcode/year2022/Day02.txt"
    }

    enum class Sign(val score: Int) { Rock(1), Paper(2), Scissors(3) }

    private val rounds = input.map {
        val (player1, player2) = it.split(" ")
        Pair(player1.toSign(), player2.toSign())
    }

    fun part1(): Int =
        rounds.fold(0) { acc, round -> acc + round.calculateScore() }

    fun part2(): Int =
        rounds.fold(0) { acc, round -> acc + round.manipulate().calculateScore() }

    private fun String.toSign(): Sign =
        when (this) {
            "A", "X" -> Rock
            "B", "Y" -> Paper
            "C", "Z" -> Scissors
            else -> error("unknown sign $this")
        }

    private fun Pair<Sign, Sign>.calculateScore(): Int =
        when (first to second) {
            Scissors to Rock, Paper to Scissors, Rock to Paper -> 6 + second.score
            Rock to Rock, Paper to Paper, Scissors to Scissors -> 3 + second.score
            else -> second.score
        }

    private fun Pair<Sign, Sign>.manipulate(): Pair<Sign, Sign> =
        when (first to second) {
            Rock to Rock -> Pair(Rock, Scissors)
            Scissors to Rock -> Pair(Scissors, Paper)
            Paper to Rock -> Pair(Paper, Rock)
            Rock to Paper -> Pair(Rock, Rock)
            Scissors to Paper -> Pair(Scissors, Scissors)
            Paper to Paper -> Pair(Paper, Paper)
            Rock to Scissors -> Pair(Rock, Paper)
            Scissors to Scissors -> Pair(Scissors, Rock)
            Paper to Scissors -> Pair(Paper, Scissors)
            else -> this
        }
}

fun main() {
    val input = Day02::class.java.getResource(Day02.input)!!.readText().trim().split("\n", "\r\n")
    val day02 = Day02(input)
    println("Day02::part1 -> ${day02.part1()}") // 14531
    println("Day02::part2 -> ${day02.part2()}") // 11258
}
