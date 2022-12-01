package at.gnu.adventofcode.year2022

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class Day01Test {

    private val calories =
        listOf("1000", "2000", "3000", "", "4000", "", "5000", "6000", "", "7000", "8000", "9000", "", "10000")

    private val test = mapOf(
        Day01::part1 to 24000,
        Day01::part2 to 45000
    )

    @Test @ExperimentalTime
    fun testMySolution() {
        val day01 = Day01(calories)
        for (function in test.keys) {
            val (result, time) = measureTimedValue { function(day01) }
            println("Day01::${function.name}: $calories -> $result [$time]")
            assertEquals(test[function], result)
        }
    }
}
