package at.gnu.adventofcode.year2022

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class Day05Test {

    private val configuration = listOf("    [D]    ", "[N] [C]    ", "[Z] [M] [P]", "1   2   3 ")
    private val commands = """
            move 1 from 2 to 1
            move 3 from 1 to 3
            move 2 from 2 to 1
            move 1 from 1 to 2
    """.trimIndent().split("\n")

    private val test = mapOf(
        Day05::part1 to "CMZ",
        Day05::part2 to "MCD"
    )

    @Test @ExperimentalTime
    fun testMySolution() {
        val day05 = Day05(configuration, commands)
        for (function in test.keys) {
            val (result, time) = measureTimedValue { function(day05) }
            val stacks = (configuration.first().length + 1) / 4
            println("Day05::${function.name}: $stacks stacks, ${commands.size} commands -> $result [$time]")
            assertEquals(test[function], result)
        }
    }
}
