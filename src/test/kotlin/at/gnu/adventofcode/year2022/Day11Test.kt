package at.gnu.adventofcode.year2022

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class Day11Test {

    private val monkeys = listOf(
        """
        Monkey 0:
          Starting items: 79, 98
          Operation: new = old * 19
          Test: divisible by 23
            If true: throw to monkey 2
            If false: throw to monkey 3
        """.trimIndent(),
        """
        Monkey 1:
          Starting items: 54, 65, 75, 74
          Operation: new = old + 6
          Test: divisible by 19
            If true: throw to monkey 2
            If false: throw to monkey 0
        """.trimIndent(),
        """
        Monkey 2:
          Starting items: 79, 60, 97
          Operation: new = old * old
          Test: divisible by 13
            If true: throw to monkey 1
            If false: throw to monkey 3
        """.trimIndent(),
        """
        Monkey 3:
          Starting items: 74
          Operation: new = old + 3
          Test: divisible by 17
            If true: throw to monkey 0
            If false: throw to monkey 1
        """.trimIndent()
    )

    private val test = mapOf(
        Day11::part1 to 10605L,
        Day11::part2 to 2713310158L
    )

    @Test @ExperimentalTime
    fun testMySolution() {
        val day11 = Day11(monkeys)
        for (function in test.keys) {
            val (result, time) = measureTimedValue { function(day11) }
            println("Day11::${function.name}: ${monkeys.size} monkeys -> $result [$time]")
            assertEquals(test[function], result)
        }
    }
}
