package at.gnu.adventofcode.year2022

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class Day20Test {

    private val numbers = listOf(1, 2, -3, 3, -2, 0, 4)

    private val test = mapOf(
        Day20::part1 to 3L,
        Day20::part2 to 1623178306L
    )

    @Test @ExperimentalTime
    fun testMySolution() {
        val day20 = Day20(numbers)
        for (function in test.keys) {
            val (result, time) = measureTimedValue { function(day20) }
            println("Day20::${function.name}: ${numbers.size} numbers -> $result [$time]")
            assertEquals(test[function], result)
        }
    }
}
