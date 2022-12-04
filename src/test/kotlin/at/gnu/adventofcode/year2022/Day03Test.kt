package at.gnu.adventofcode.year2022

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class Day03Test {

    private val rounds = listOf("vJrwpWtwJgWrhcsFMMfFFhFp", "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL", "PmmdzqPrVvPwwTWBwg",
        "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn", "ttgJtRGJQctTZtZT", "CrZsJsPPZsGzwwsLwLmpwMDw")

    private val test = mapOf(
        Day03::part1 to 157,
        Day03::part2 to 70
    )

    @Test @ExperimentalTime
    fun testMySolution() {
        val day03 = Day03(rounds)
        for (function in test.keys) {
            val (result, time) = measureTimedValue { function(day03) }
            println("Day03::${function.name}: $rounds -> $result [$time]")
            assertEquals(test[function], result)
        }
    }
}
