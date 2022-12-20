package at.gnu.adventofcode.year2022

class Day10(input: List<String>) {

    companion object {
        const val resource = "/adventofcode/year2022/Day10.txt"
    }

    class State(val ip: Int, val x: Int)

    private val program = input.map {
        val components = it.split(" ")
        Pair(components[0], components.getOrNull(1)?.toIntOrNull() ?: 0)
    }

    private val states = calculateStates()

    fun part1(): String =
        (20..220 step 40).sumOf { it * states.xValueAt(it) }.toString()

    fun part2(): String {
        var crt = ""
        for (cycle in 0 until 240) {
            val x = states.xValueAt(cycle + 1)
            crt += if ((cycle % 40) in (x - 1)..(x + 1)) "#" else "."
        }
        return crt.chunked(40).joinToString("\n")
    }

    private fun calculateStates(): List<State> {
        var state = State(0, 1)
        val states = mutableListOf(state)
        for ((command, argument) in program) {
            states += when (command) {
                "noop" -> State(state.ip + 1, state.x)
                else -> State(state.ip + 2, state.x + argument)
            }
            state = states.last()
        }
        return states
    }

    private fun List<State>.xValueAt(cycle: Int): Int =
        last { it.ip < cycle }.x
}

fun main() {
    val day10 = Day10(Day10::class.java.getResource(Day10.resource)!!.readText().trim().split("\n", "\r\n"))
    println("Day10::part1 -> ${day10.part1()}")
    println("Day10::part2 ->\n${day10.part2()}")
}
