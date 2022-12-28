package at.gnu.adventofcode.year2022

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class Day15Test {

    private val positions = """
        Sensor at x=2, y=18: closest beacon is at x=-2, y=15
        Sensor at x=9, y=16: closest beacon is at x=10, y=16
        Sensor at x=13, y=2: closest beacon is at x=15, y=3
        Sensor at x=12, y=14: closest beacon is at x=10, y=16
        Sensor at x=10, y=20: closest beacon is at x=10, y=16
        Sensor at x=14, y=17: closest beacon is at x=10, y=16
        Sensor at x=8, y=7: closest beacon is at x=2, y=10
        Sensor at x=2, y=0: closest beacon is at x=2, y=10
        Sensor at x=0, y=11: closest beacon is at x=2, y=10
        Sensor at x=20, y=14: closest beacon is at x=25, y=17
        Sensor at x=17, y=20: closest beacon is at x=21, y=22
        Sensor at x=16, y=7: closest beacon is at x=15, y=3
        Sensor at x=14, y=3: closest beacon is at x=15, y=3
        Sensor at x=20, y=1: closest beacon is at x=15, y=3
        """.trimIndent().split("\n")

    private val test = mapOf(
        Pair(Day15::part1, 10) to 26L,
        Pair(Day15::part2, 20) to 56000011L
    )

    @Test @ExperimentalTime
    fun testMySolution() {
        val day15 = Day15(positions)
        for (input in test.keys) {
            val (result, time) = measureTimedValue { input.first(day15, input.second) }
            println("Day15::${input.first.name}: ${positions.size} positions -> $result [$time]")
            assertEquals(test[input], result)
        }
    }
}
