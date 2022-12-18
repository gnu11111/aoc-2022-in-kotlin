package at.gnu.adventofcode.year2022

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class Day12Test {

    private val map = listOf("Sabqponm", "abcryxxl", "accszExk", "acctuvwj", "abdefghi")

    private val test = mapOf(
        Day12::part1 to 31,
        Day12::part2 to 29
    )

    @Test @ExperimentalTime
    fun testMySolution() {
        val day12 = Day12(map)
        for (function in test.keys) {
            val (result, time) = measureTimedValue { function(day12) }
            println("Day12::${function.name}: $map -> $result [$time]")
            assertEquals(test[function], result)
        }
    }
}
