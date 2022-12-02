package at.gnu.adventofcode.year2022

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class Day02Test {

    private val rounds = listOf("A Y", "B X", "C Z")

    private val test = mapOf(
        Day02::part1 to 15,
        Day02::part2 to 12
    )

    @Test @ExperimentalTime
    fun testMySolution() {
        val day02 = Day02(rounds)
        for (function in test.keys) {
            val (result, time) = measureTimedValue { function(day02) }
            println("Day02::${function.name}: $rounds -> $result [$time]")
            assertEquals(test[function], result)
        }
    }
}
