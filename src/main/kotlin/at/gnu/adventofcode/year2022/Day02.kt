package at.gnu.adventofcode.year2022

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
            "A", "X" -> Sign.Rock
            "B", "Y" -> Sign.Paper
            "C", "Z" -> Sign.Scissors
            else -> error("unknown sign $this")
        }

    private fun Pair<Sign, Sign>.calculateScore(): Int =
        when {
            (first == second) -> 3 + second.score
            (second == Sign.Rock) && (first == Sign.Scissors) -> 6 + Sign.Rock.score
            (second == Sign.Scissors) && (first == Sign.Paper) -> 6 + Sign.Scissors.score
            (second == Sign.Paper) && (first == Sign.Rock) -> 6 + Sign.Paper.score
            else -> second.score
        }

    private fun Pair<Sign, Sign>.manipulate(): Pair<Sign, Sign> =
        when {
            (second == Sign.Rock) && (first == Sign.Rock) -> Pair(Sign.Rock, Sign.Scissors)
            (second == Sign.Rock) && (first == Sign.Scissors) -> Pair(Sign.Scissors, Sign.Paper)
            (second == Sign.Rock) && (first == Sign.Paper) -> Pair(Sign.Paper, Sign.Rock)
            (second == Sign.Paper) && (first == Sign.Rock) -> Pair(Sign.Rock, Sign.Rock)
            (second == Sign.Paper) && (first == Sign.Scissors) -> Pair(Sign.Scissors, Sign.Scissors)
            (second == Sign.Paper) && (first == Sign.Paper) -> Pair(Sign.Paper, Sign.Paper)
            (second == Sign.Scissors) && (first == Sign.Rock) -> Pair(Sign.Rock, Sign.Paper)
            (second == Sign.Scissors) && (first == Sign.Scissors) -> Pair(Sign.Scissors, Sign.Rock)
            (second == Sign.Scissors) && (first == Sign.Paper) -> Pair(Sign.Paper, Sign.Scissors)
            else -> this
        }
}

fun main() {
    val input = Day02::class.java.getResource(Day02.input)!!.readText().trim().split("\n", "\r\n")
    val day02 = Day02(input)
    println("Day02::part1 -> ${day02.part1()}")
    println("Day02::part2 -> ${day02.part2()}")
}
