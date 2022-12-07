package at.gnu.adventofcode.year2022

class Day06(private val datastream: String) {

    companion object {
        const val input = "/adventofcode/year2022/Day06.txt"
    }

    fun part1(): Int =
        indexOfMarker(distinctChars = 4)

    fun part2(): Int =
        indexOfMarker(distinctChars = 14)

    private fun indexOfMarker(distinctChars: Int) =
        datastream.windowed(distinctChars, 1).indexOfFirst { it.toSet().size == distinctChars } + distinctChars
}

fun main() {
    val day06 = Day06(Day06::class.java.getResource(Day06.input)!!.readText().trim())
    println("Day06::part1 -> ${day06.part1()}")
    println("Day06::part2 -> ${day06.part2()}")
}
