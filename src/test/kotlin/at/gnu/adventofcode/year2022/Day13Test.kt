package at.gnu.adventofcode.year2022

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class Day13Test {

    private val packets = listOf(
        Pair("[1,1,3,1,1]", "[1,1,5,1,1]"),
        Pair("[[1],[2,3,4]]", "[[1],4]"),
        Pair("[9]", "[[8,7,6]]"),
        Pair("[[4,4],4,4]", "[[4,4],4,4,4]"),
        Pair("[7,7,7,7]", "[7,7,7]"),
        Pair("[]", "[3]"),
        Pair("[[[]]]", "[[]]"),
        Pair("[1,[2,[3,[4,[5,6,7]]]],8,9]", "[1,[2,[3,[4,[5,6,0]]]],8,9]")
    )

    private val test = mapOf(
        Day13::part1 to 13,
        Day13::part2 to 140
    )

    @Test @ExperimentalTime
    fun testMySolution() {
        val day13 = Day13(packets)
        for (function in test.keys) {
            val (result, time) = measureTimedValue { function(day13) }
            println("Day13::${function.name}: ${packets.size} packet pairs -> $result [$time]")
            assertEquals(test[function], result)
        }
    }
}
