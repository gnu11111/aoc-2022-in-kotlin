package at.gnu.adventofcode.year2022

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class Day21Test {

    private val monkeys = """
        root: pppw + sjmn
        dbpl: 5
        cczh: sllz + lgvd
        zczc: 2
        ptdq: humn - dvpt
        dvpt: 3
        lfqf: 4
        humn: 5
        ljgn: 2
        sjmn: drzm * dbpl
        sllz: 4
        pppw: cczh / lfqf
        lgvd: ljgn * ptdq
        drzm: hmdt - zczc
        hmdt: 32
    """.trimIndent().split("\n")

    private val test = mapOf(
        Day21::part1 to 152L,
//        Day21::part2 to 301L
    )

    @Test @ExperimentalTime
    fun testMySolution() {
        val day21 = Day21(monkeys)
        for (function in test.keys) {
            val (result, time) = measureTimedValue { function(day21) }
            println("Day21::${function.name}: ${monkeys.size} monkeys -> $result [$time]")
            assertEquals(test[function], result)
        }
    }
}
