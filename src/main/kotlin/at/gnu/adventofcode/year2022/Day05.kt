package at.gnu.adventofcode.year2022

import kotlin.math.min

class Day05(configuration: List<String>, rearrangement: List<String>) {

    companion object {
        const val RESOURCE = "/adventofcode/year2022/Day05.txt"
        val command = """move (\d+) from (\d+) to (\d+)""".toRegex()
    }

    private val commands = rearrangement.mapNotNull {
        if (it.isBlank())
            null
        else {
            val (_, amount, source, destination) = command.matchEntire(it)!!.groupValues
            Triple(amount.toInt(), source.toInt(), destination.toInt())
        }
    }

    private val stacks: Map<Int, List<Char>> = buildMap {
        val amountOfStacks = configuration.maxOf { it.length + 1 } / 4
        val temp = mutableMapOf<Int, List<Char>>()
        for (i in 0 until amountOfStacks)
            temp[i + 1] = mutableListOf()
        for (i in (configuration.size - 2) downTo 0)
            for (j in 0 until amountOfStacks) {
                val crate = configuration[i].getOrNull((j * 4) + 1) ?: continue
                if (crate != ' ')
                    temp[j + 1] = temp[j + 1]!!.plus(crate)
            }
        temp.forEach { put(it.key, it.value) }
    }

    fun part1(): String =
        commands.fold(stacks) { acc, (amount, source, destination) ->
            val temp = acc.toMutableMap()
            repeat(amount) {
                if (temp[source]!!.isNotEmpty()) {
                    temp[destination] = temp[destination]!!.plus(temp[source]!!.last())
                    temp[source] = temp[source]!!.dropLast(1)
                }
            }
            temp
        }.values.map { it.last() }.joinToString("")

    fun part2(): String =
        commands.fold(stacks) { acc, (amount, source, destination) ->
            val temp = acc.toMutableMap()
            val realAmount = min(amount, temp[source]!!.size)
            temp[destination] = temp[destination]!!.plus(temp[source]!!.takeLast(realAmount))
            temp[source] = temp[source]!!.dropLast(realAmount)
            temp
        }.values.map { it.last() }.joinToString("")
}

fun main() {
    val input = Day05::class.java.getResource(Day05.RESOURCE)!!.readText().split("\n\n", "\r\n\r\n").map {
        it.split("\n", "\r\n")
    }
    val day05 = Day05(input.first(), input.last())
    println("Day05::part1 -> ${day05.part1()}")
    println("Day05::part2 -> ${day05.part2()}")
}
