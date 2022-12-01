package at.gnu.adventofcode.year2022

class Day01(calories: List<String>) {

    companion object {
        const val input = "/adventofcode/year2022/Day01.txt"
    }

    private val caloriesPerElf: List<List<Int>>
    init {
        val elves = mutableListOf<List<Int>>()
        var elf = mutableListOf<Int>()
        elves.add(elf)
        for (line in calories) {
            if (line.isBlank()) {
                elf = mutableListOf()
                elves.add(elf)
            } else
                elf += line.toInt()
        }
        caloriesPerElf = elves.toList()
    }

    fun part1(): Int =
        caloriesPerElf.maxOfOrNull { it.sum() } ?: 0

    fun part2(): Int =
        caloriesPerElf.map { it.sum() }.sortedDescending().take(3).sum()
}

fun main() {
    val day01 = Day01(Day01::class.java.getResource(Day01.input)!!.readText().trim().split("\n", "\r\n"))
    println("Day01::part1 -> ${day01.part1()}")
    println("Day01::part2 -> ${day01.part2()}")
}
