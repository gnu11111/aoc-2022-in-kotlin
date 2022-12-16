package at.gnu.adventofcode.year2022

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class Day14Test {

    private val pathes = listOf("498,4 -> 498,6 -> 496,6", "503,4 -> 502,4 -> 502,9 -> 494,9")

    private val test = mapOf(
        Day14::part1 to 24,
        Day14::part2 to 93
    )

    @Test @ExperimentalTime
    fun testMySolution() {
        val day14 = Day14(pathes)
        for (function in test.keys) {
            val (result, time) = measureTimedValue { function(day14) }
            println("Day14::${function.name}: $pathes -> $result [$time]")
            assertEquals(test[function], result)
        }
    }
}
