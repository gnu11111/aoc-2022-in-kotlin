package at.gnu.adventofcode.year2022

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class Day07Test {

    private val input = """
        $ cd /
        $ ls
        dir a
        14848514 b.txt
        8504156 c.dat
        dir d
        $ cd a
        $ ls
        dir e
        29116 f
        2557 g
        62596 h.lst
        $ cd e
        $ ls
        584 i
        $ cd ..
        $ cd ..
        $ cd d
        $ ls
        4060174 j
        8033020 d.log
        5626152 d.ext
        7214296 k
    """.trimIndent().split("\n")

    private val test = mapOf(
        Day07::part1 to 95437,
        Day07::part2 to 24933642
    )

    @Test @ExperimentalTime
    fun testMySolution() {
        val day07 = Day07(input)
        for (function in test.keys) {
            val (result, time) = measureTimedValue { function(day07) }
            println("Day07::${function.name}: ${input.size} lines -> $result [$time]")
            assertEquals(test[function], result)
        }
    }
}
