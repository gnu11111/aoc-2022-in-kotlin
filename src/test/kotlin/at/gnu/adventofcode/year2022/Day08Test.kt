package at.gnu.adventofcode.year2022

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class Day08Test {

    private val trees = listOf("30373", "25512", "65332", "33549", "35390")

    private val test = mapOf(
        Day08::part1 to 21,
        Day08::part2 to 8
    )

    @Test @ExperimentalTime
    fun testMySolution() {
        val day08 = Day08(trees)
        for (function in test.keys) {
            val (result, time) = measureTimedValue { function(day08) }
            println("Day08::${function.name}: $trees -> $result [$time]")
            assertEquals(test[function], result)
        }
    }
}
