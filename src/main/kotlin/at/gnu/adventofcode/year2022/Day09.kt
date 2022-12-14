package at.gnu.adventofcode.year2022

class Day09(input: List<String>) {

    companion object {
        const val input = "/adventofcode/year2022/Day09.txt"
    }

    data class MovingObject(var x: Int = 0, var y: Int = 0) {

        infix fun follows(movingObject: MovingObject) {
            val dx = movingObject.x - x
            val dy = movingObject.y - y
            when {
                (dx > 1) && (dy > 0) -> { x++; y++ }
                (dx > 1) && (dy < 0) -> { x++; y-- }
                (dx < -1) && (dy > 0) -> { x--; y++ }
                (dx < -1) && (dy < 0) -> { x--; y-- }
                (dy > 1) && (dx > 0) -> { y++; x++ }
                (dy > 1) && (dx < 0) -> { y++; x-- }
                (dy < -1) && (dx > 0) -> { y--; x++ }
                (dy < -1) && (dx < 0) -> { y--; x-- }
                (dx > 1) -> { x++ }
                (dx < -1) -> { x-- }
                (dy > 1) -> { y++ }
                (dy < -1) -> { y-- }
            }
        }
    }

    private val movements = input.map { line -> line.split(" ").let { it[0] to it[1].toInt() }}


    fun part1(): Int =
        calculateVisitedPositions(tailLength = 1).size

    fun part2(): Int =
        calculateVisitedPositions(tailLength = 9).size

    private fun calculateVisitedPositions(tailLength: Int): Set<String> {
        val visitedPositions = mutableSetOf<String>()
        val head = MovingObject()
        val tails = List(tailLength) { MovingObject() }
        for ((direction, amount) in movements) {
            for (i in 1..amount) {
                when (direction) {
                    "R" -> head.x++
                    "L" -> head.x--
                    "U" -> head.y--
                    "D" -> head.y++
                }
                tails.first() follows head
                tails.zipWithNext().forEach { it.second follows it.first }
                visitedPositions += tails.last().toString()
            }
        }
        return visitedPositions
    }
}

fun main() {
    val day09 = Day09(Day09::class.java.getResource(Day09.input)!!.readText().trim().split("\n", "\r\n"))
    println("Day09::part1 -> ${day09.part1()}")
    println("Day09::part2 -> ${day09.part2()}")
}
