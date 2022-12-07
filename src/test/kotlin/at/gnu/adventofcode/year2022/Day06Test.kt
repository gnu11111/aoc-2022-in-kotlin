package at.gnu.adventofcode.year2022

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class Day06Test {

    private val test = mapOf(
        Pair(Day06::part1, "mjqjpqmgbljsphdztnvjfqwrcgsmlb") to 7,
        Pair(Day06::part1, "bvwbjplbgvbhsrlpgdmjqwftvncz") to 5,
        Pair(Day06::part1, "nppdvjthqldpwncqszvftbrmjlhg") to 6,
        Pair(Day06::part1, "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") to 10,
        Pair(Day06::part1, "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") to 11,
        Pair(Day06::part2, "mjqjpqmgbljsphdztnvjfqwrcgsmlb") to 19,
        Pair(Day06::part2, "bvwbjplbgvbhsrlpgdmjqwftvncz") to 23,
        Pair(Day06::part2, "nppdvjthqldpwncqszvftbrmjlhg") to 23,
        Pair(Day06::part2, "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") to 29,
        Pair(Day06::part2, "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") to 26
    )

    @Test @ExperimentalTime
    fun testMySolution() {
        for (input in test.keys) {
            val (result, time) = measureTimedValue { input.first(Day06(input.second)) }
            println("Day06::${input.first.name}: ${input.second} -> $result [$time]")
            assertEquals(test[input], result)
        }
    }
}
