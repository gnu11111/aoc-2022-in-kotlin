package at.gnu.adventofcode.year2022

class Day04(sectionAssignmentPairs: List<String>) {

    companion object {
        const val resource = "/adventofcode/year2022/Day04.txt"
    }

    private val sections = sectionAssignmentPairs.map {
        val elves = it.split(",")
        val range1 = elves[0].split("-")
        val range2 = elves[1].split("-")
        Pair((range1[0].toInt()..range1[1].toInt()).toSet(), (range2[0].toInt()..range2[1].toInt()).toSet())
    }

    fun part1(): Int =
        sections.count { it.first.containsAll(it.second) || it.second.containsAll(it.first) }

    fun part2(): Int =
        sections.count { (it.first intersect it.second).isNotEmpty() }
}

fun main() {
    val input = Day04::class.java.getResource(Day04.resource)!!.readText().trim().split("\n", "\r\n")
    val day04 = Day04(input)
    println("Day04::part1 -> ${day04.part1()}")
    println("Day04::part2 -> ${day04.part2()}")
}
