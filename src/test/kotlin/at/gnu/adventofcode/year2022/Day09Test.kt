package at.gnu.adventofcode.year2022

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class Day09Test {

    private val inputs = listOf(listOf("R 4", "U 4", "L 3", "D 1", "R 4", "D 1", "L 5", "R 2"),
        listOf("R 5", "U 8", "L 8", "D 3", "R 17",  "D 10", "L 25", "U 20"))

    private val test = mapOf(
        Day09::part1 to inputs[0] to 13,
        Day09::part2 to inputs[0] to 1,
        Day09::part2 to inputs[1] to 36
    )

    @Test @ExperimentalTime
    fun testMySolution() {
        for ((function, input) in test.keys) {
            val (result, time) = measureTimedValue { function.invoke(Day09(input)) }
            println("Day09::${function.name}: $input -> $result [${time}]")
            assertEquals(test[function to input], result)
        }
    }
}
