package at.gnu.adventofcode.year2022

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class Day04Test {

    private val sectionAssignmentPairs = listOf("2-4,6-8", "2-3,4-5", "5-7,7-9", "2-8,3-7", "6-6,4-6", "2-6,4-8")

    private val test = mapOf(
        Day04::part1 to 2,
        Day04::part2 to 4
    )

    @Test @ExperimentalTime
    fun testMySolution() {
        val day04 = Day04(sectionAssignmentPairs)
        for (function in test.keys) {
            val (result, time) = measureTimedValue { function(day04) }
            println("Day04::${function.name}: $sectionAssignmentPairs -> $result [$time]")
            assertEquals(test[function], result)
        }
    }
}
