package at.gnu.adventofcode.year2022

import kotlin.math.abs
import kotlin.math.max

class Day09(input: List<String>) {

    companion object {
        const val resource = "/adventofcode/year2022/Day09.txt"
    }

    data class Position(var x: Int = 0, var y: Int = 0) {

        infix fun follows(movingObject: Position) {
            val dx = movingObject.x - x
            val dy = movingObject.y - y
            val distance = max(abs(dx), abs(dy))
            if (distance > 1) {
                x += dx.coerceIn(-1, 1)
                y += dy.coerceIn(-1, 1)
            }
        }
    }

    private val movements = input.map { line -> line.split(" ").let { it[0] to it[1].toInt() }}


    fun part1(): Int =
        calculateVisitedPositions().size

    fun part2(): Int =
        calculateVisitedPositions(tailLength = 9).size

    private fun calculateVisitedPositions(tailLength: Int = 1): Set<String> {
        val visitedPositions = mutableSetOf<String>()
        val head = Position()
        val tail = List(tailLength) { Position() }
        for ((direction, amount) in movements) {
            repeat(amount) {
                when (direction) {
                    "R" -> head.x++
                    "L" -> head.x--
                    "U" -> head.y--
                    "D" -> head.y++
                }
                tail.first() follows head
                tail.zipWithNext().forEach { it.second follows it.first }
                visitedPositions += tail.last().toString()
            }
        }
        return visitedPositions
    }
}

fun main() {
    val day09 = Day09(Day09::class.java.getResource(Day09.resource)!!.readText().trim().split("\n", "\r\n"))
    println("Day09::part1 -> ${day09.part1()}")
    println("Day09::part2 -> ${day09.part2()}")
}
