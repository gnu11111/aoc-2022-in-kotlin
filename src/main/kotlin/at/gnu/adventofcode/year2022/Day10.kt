package at.gnu.adventofcode.year2022

class Day10(input: List<String>) {

    companion object {
        const val input = "/adventofcode/year2022/Day10.txt"
    }

    private val program = input.map {
        val components = it.split(" ")
        Pair(components[0], components.getOrNull(1)?.toIntOrNull() ?: 0)
    }

    private val states = calculateStates()

    fun part1(): String =
        (20..220 step 40).sumOf { it * states.positionAt(it) }.toString()

    fun part2(): String {
        var crt = ""
        for (cycle in 0..239) {
            val x = states.positionAt(cycle + 1)
            crt += if (((x - 1) == (cycle % 40)) || (x == (cycle % 40)) || ((x + 1) == (cycle % 40))) "#" else "."
        }
        return crt.chunked(40).joinToString("\n")
    }

    private fun calculateStates(): MutableList<Pair<Int, Int>> {
        val states = mutableListOf(Pair(0, 1))
        for ((command, argument) in program) {
            states += when (command) {
                "noop" -> Pair(states.last().first + 1, states.last().second)
                else -> Pair(states.last().first + 2, states.last().second + argument)
            }
        }
        return states
    }

    private fun List<Pair<Int, Int>>.positionAt(cycle: Int): Int =
        this.last { it.first < cycle }.second
}

fun main() {
    val day10 = Day10(Day10::class.java.getResource(Day10.input)!!.readText().trim().split("\n", "\r\n"))
    println("Day10::part1 -> ${day10.part1()}")
    println("Day10::part2 ->\n${day10.part2()}")
}
