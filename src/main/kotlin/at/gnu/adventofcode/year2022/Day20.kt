package at.gnu.adventofcode.year2022


class Day20(val input: List<Int>) {

    companion object {
        const val RESOURCE = "/adventofcode/year2022/Day20.txt"
        const val DECRYPTION_KEY = 811_589_153
    }

    data class Number(val value: Long, val initialIndex: Int = 0)

    private val numbers = input.mapIndexed { i, number -> Number(number.toLong(), i) }


    fun part1(): Long =
        numbers.toMutableList().decrypt().calculateSumOfCoordinates()

    fun part2(): Long {
        val actualNumbers = numbers.map { Number(it.value * DECRYPTION_KEY, it.initialIndex) }.toMutableList()
        return actualNumbers.decrypt(10).calculateSumOfCoordinates()
    }


    private fun MutableList<Number>.decrypt(times: Int = 1): MutableList<Number> {
        repeat(times) {
            for (i in indices) {
                val index = indexOfFirst { it.initialIndex == i }
                val number = removeAt(index)
                add((index + number.value).mod(size), number)
            }
        }
        return this
    }

    private fun MutableList<Number>.calculateSumOfCoordinates(): Long {
        val index = indexOfFirst { it.value == 0L }
        return listOf(1000, 2000, 3000).sumOf { this[(index + it) % size].value }
    }
}

fun main() {
    val input = Day20::class.java.getResource(Day20.RESOURCE)!!.readText().trim().split("\n", "\r\n").map(String::toInt)
    val day20 = Day20(input)
    println("Day20::part1 -> ${day20.part1()}")
    println("Day20::part2 -> ${day20.part2()}")
}
