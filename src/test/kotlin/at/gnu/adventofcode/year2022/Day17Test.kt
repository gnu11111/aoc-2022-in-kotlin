package at.gnu.adventofcode.year2022

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class Day17Test {

    private val jetPattern = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>"

    private val test = mapOf(
        Day17::part1 to 3068L,
        Day17::part2 to 1514285714288L
    )

    @Test @ExperimentalTime
    fun testMySolution() {
        val day17 = Day17(jetPattern)
        for (function in test.keys) {
            val (result, time) = measureTimedValue { function(day17) }
            println("Day17::${function.name}: pattern-length ${jetPattern.length} -> $result [$time]")
            assertEquals(test[function], result)
        }
    }
}
