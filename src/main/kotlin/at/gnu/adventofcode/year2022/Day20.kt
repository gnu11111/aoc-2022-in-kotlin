package at.gnu.adventofcode.year2022

class Day20(val input: List<Int>) {

    companion object {
        const val resource = "/adventofcode/year2022/Day20.txt"
    }

    data class Number(val value: Long, val initialIndex: Int = 0)

    private val initialNumbers = input.mapIndexed { i, number -> Number(number.toLong(), i) }


    fun part1(): Long {
        val numbers = initialNumbers.toMutableList()
        numbers.decrypt()
        return calculateSumOfCoordinates(numbers)
    }

    fun part2(): Long {
        val numbers = initialNumbers.map { Number(it.value * 811_589_153, it.initialIndex) }.toMutableList()
        numbers.decrypt(10)
        return calculateSumOfCoordinates(numbers)
    }


    private fun MutableList<Number>.decrypt(times: Int = 1) {
        repeat(times) {
            for (i in indices) {
                val index = indexOfFirst { it.initialIndex == i }
                val number = removeAt(index)
                add((index + number.value).mod(size), number)
            }
        }
    }

    private fun calculateSumOfCoordinates(numbers: MutableList<Number>): Long {
        val index = numbers.indexOfFirst { it.value == 0L }
        return listOf(1000, 2000, 3000).sumOf { numbers[(index + it) % numbers.size].value }
    }
}

fun main() {
    val input = Day20::class.java.getResource(Day20.resource)!!.readText().trim().split("\n", "\r\n").map(String::toInt)
    val day20 = Day20(input)
    println("Day20::part1 -> ${day20.part1()}")
    println("Day20::part2 -> ${day20.part2()}")
}
