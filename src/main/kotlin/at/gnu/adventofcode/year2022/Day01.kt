package at.gnu.adventofcode.year2022

class Day01(private val caloriesPerElf: List<List<Int>>) {

    companion object {
        const val RESOURCE = "/adventofcode/year2022/Day01.txt"
    }

    fun part1(): Int =
        caloriesPerElf.maxOfOrNull { it.sum() } ?: 0

    fun part2(): Int =
        caloriesPerElf.map { it.sum() }.sortedDescending().take(3).sum()
}

fun main() {
    val input = Day01::class.java.getResource(Day01.RESOURCE)!!.readText().trim().split("\n\n", "\r\n\r\n").map {
        it.lines().map(String::toInt)
    }
    val day01 = Day01(input)
    println("Day01::part1 -> ${day01.part1()}")
    println("Day01::part2 -> ${day01.part2()}")
}
