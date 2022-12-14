package at.gnu.adventofcode.year2022

class Day09(val movements: List<String>) {

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

    fun part1(): Int =
        calculateVisitedPositions(tailLength = 1).size

    fun part2(): Int =
        calculateVisitedPositions(tailLength = 9).size

    private fun calculateVisitedPositions(tailLength: Int): Set<Pair<Int, Int>> {
        val visitedPositions = mutableSetOf<Pair<Int, Int>>()
        val head = MovingObject()
        val tails = List(tailLength) { MovingObject() }
        for (movement in movements) {
            val (direction, amount) = movement.split(" ").let { it[0] to it[1].toInt() }
            for (i in 1..amount) {
                when (direction) {
                    "R" -> head.x++
                    "L" -> head.x--
                    "U" -> head.y--
                    "D" -> head.y++
                }
                tails.first() follows head
                (1 until tailLength).forEach { tails[it] follows tails[it - 1] }
                visitedPositions += tails.last().x to tails.last().y
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
