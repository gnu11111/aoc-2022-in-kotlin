package at.gnu.adventofcode.year2022

import kotlin.math.max
import kotlin.math.min

class Day14(pathes: List<String>) {

    companion object {
        const val resource = "/adventofcode/year2022/Day14.txt"
        val hole = Pair(500, 0)
    }

    private val rocks = pathes.flatMap {
        it.split(" -> ").zipWithNext().flatMap { (start, end) ->
            val rocks = mutableSetOf<Pair<Int, Int>>()
            val (x1, y1) = start.split(",").map(String::toInt)
            val (x2, y2) = end.split(",").map(String::toInt)
            if (y1 == y2)
                for (i in min(x1, x2)..max(x1, x2))
                    rocks += Pair(i, y1)
            else
                for (i in min(y1, y2)..max(y1, y2))
                    rocks += Pair(x1, i)
            rocks
        }
    }.toSet()
    private var minX = rocks.minOf { it.first }
    private var maxX = rocks.maxOf { it.first }
    private val maxY = rocks.maxOf { it.second } + 2


    fun part1(): Int =
        rocks.toMutableSet().countDrops()

    fun part2(): Int {
        val blocks = rocks.toMutableSet()
        minX = hole.first - maxY
        maxX = hole.first + maxY
        for (i in minX..maxX)
            blocks += Pair(i, maxY)
        return blocks.countDrops()
    }

    private fun MutableSet<Pair<Int, Int>>.countDrops(): Int {
        var count = -1
        var sand = first()
        while (sand != hole) {
            count++
            sand = hole.fallingDownUntil(this) ?: return count
            while (true) {
                while (Pair(sand.first - 1, sand.second + 1) !in this)
                    sand = Pair(sand.first - 1, sand.second + 1).fallingDownUntil(this) ?: return count
                val right = Pair(sand.first + 1, sand.second + 1)
                if (right in this)
                    break
                sand = right.fallingDownUntil(this) ?: return count
            }
            this += sand.copy()
        }
        return count + 1
    }

    private fun Pair<Int, Int>.fallingDownUntil(blocks: Set<Pair<Int, Int>>): Pair<Int, Int>? {
        var drop = this
        while (Pair(drop.first, drop.second + 1) !in blocks && !drop.isOutOfBounds())
            drop = Pair(drop.first, drop.second + 1)
        return if (drop.isOutOfBounds()) null else drop
    }

    private fun Pair<Int, Int>.isOutOfBounds(): Boolean =
        first !in (minX..maxX) || second !in (0..maxY)

    @Suppress("unused")
    private fun Set<Pair<Int, Int>>.visualize() {
        for (y in 0..maxY) {
            for (x in this.minOf { it.first }..this.maxOf { it.first }) {
                if (Pair(x, y) in rocks)
                    print("#")
                else if (Pair(x, y) in this)
                    print("o")
                else
                    print(".")
            }
            println()
        }
        println()
    }
}

fun main() {
    val day14 = Day14(Day14::class.java.getResource(Day14.resource)!!.readText().trim().split("\n", "\r\n"))
    println("Day14::part1 -> ${day14.part1()}")
    println("Day14::part2 -> ${day14.part2()}")
}
