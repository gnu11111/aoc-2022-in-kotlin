package at.gnu.adventofcode.year2022

import java.util.*
import kotlin.math.sqrt

class Day12(input: List<String>) {

    companion object {
        const val input = "/adventofcode/year2022/Day12.txt"
        val outOfBounds = Tile(-1, -1, 30)
    }

    class Tile(val x: Int, val y: Int, val height: Int, var f: Double = 0.0, var g: Int = 0,
        var predecessor: Tile? = null) : Comparable<Tile> {
        override fun compareTo(other: Tile): Int =
            this.f.compareTo(other.f)
    }

    private lateinit var start: Tile
    private lateinit var end: Tile
    private val area = input.mapIndexed { y, row -> row.mapIndexed { x, c ->
        when (c) {
            'S' -> let { start = Tile(x, y, 0); start }
            'E' -> let { end = Tile(x, y, 25); end }
            else -> Tile(x, y, c - 'a')
        } } }


    fun part1(): Int =
        calculateStepsOfShortestPath(start, end)

    fun part2(): Int =
        area.flatMap { it.filter { tile -> tile.height == 0 } }.minOf { calculateStepsOfShortestPath(it, end) }

    private fun calculateStepsOfShortestPath(from: Tile, to: Tile, h: (Tile, Tile) -> Double = ::aStar): Int {
        resetPathDataInTiles()
        val openList = PriorityQueue<Tile>().apply { add(from) }
        val closedList = mutableSetOf<Tile>()
        while (openList.isNotEmpty()) {
            val currentTile = openList.poll()
            if (currentTile === to)
                return currentTile.g
            closedList += currentTile
            currentTile.expand(to, openList, closedList, h)
        }
        return Int.MAX_VALUE
    }

    private fun Tile.expand(to: Tile, openList: PriorityQueue<Tile>, closedList: Set<Tile>, h: (Tile, Tile) -> Double) {
        for (successor in neighbors()) {
            // do not consider height differences of more than 1 from here to the successor
            if ((successor in closedList) || (successor.height > (height + 1)))
                continue
            val tentativeG = g + 1 // the cost to get to the successor is always 1
            if ((successor in openList) && (tentativeG >= successor.g))
                continue
            successor.predecessor = this
            successor.g = tentativeG
            successor.f = tentativeG + h(successor, to)
            if (successor !in openList)
                openList += successor
        }
    }

    @Suppress("unused", "UNUSED_PARAMETER")
    private fun dijkstra(from: Tile, to: Tile): Double = 0.0

    private fun aStar(from: Tile, to: Tile): Double =
        sqrt((((from.x - to.x) * (from.x - to.x)) + ((from.y - to.y) * (from.y - to.y))).toDouble())

    private fun resetPathDataInTiles() =
        area.forEach { it.forEach { tile -> tile.f = 0.0; tile.g = 0; tile.predecessor = null } }

    private fun Tile.neighbors(): Set<Tile> =
        setOf(tileAt(x - 1, y), tileAt(x + 1, y), tileAt(x, y - 1), tileAt(x, y + 1))

    private fun tileAt(x: Int, y: Int): Tile =
        area.elementAtOrNull(y)?.elementAtOrNull(x) ?: outOfBounds
}

fun main() {
    val day12 = Day12(Day12::class.java.getResource(Day12.input)!!.readText().trim().split("\n", "\r\n"))
    println("Day12::part1 -> ${day12.part1()}")
    println("Day12::part2 -> ${day12.part2()}")
}
