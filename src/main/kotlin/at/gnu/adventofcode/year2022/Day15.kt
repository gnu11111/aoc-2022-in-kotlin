package at.gnu.adventofcode.year2022

import kotlin.math.abs

class Day15(positions: List<String>) {

    companion object {
        const val RESOURCE = "/adventofcode/year2022/Day15.txt"
        val report = """Sensor at x=(.+?), y=(.+?): closest beacon is at x=(.+?), y=(.+?)""".toRegex()
    }

    sealed class Position {

        abstract val x: Int
        abstract val y: Int

        infix fun distanceTo(other: Position) =
            abs(x - other.x) + abs(y - other.y)
    }

    data class Beacon(override val x: Int, override val y: Int) : Position()
    data class Sensor(override val x: Int, override val y: Int) : Position()

    private val sensorsAndBeacons = positions.map {
        val (x1, y1, x2, y2) = report.matchEntire(it)!!.destructured
        Sensor(x1.toInt(), y1.toInt()) to Beacon(x2.toInt(), y2.toInt())
    }


    fun part1(y: Int): Long {
        val coverage = mutableSetOf<Int>()
        for ((sensor, beacon) in sensorsAndBeacons) {
            val radius = sensor distanceTo beacon
            val distance = abs(sensor.y - y)
            if (distance > radius)
                continue
            val xRange = (sensor.x - radius + distance)..(sensor.x + radius - distance)
            xRange.forEach { coverage.add(it) }
        }
        val sensorsInLine = sensorsAndBeacons.filter { (it.first.y == y) }.map { it.first }.toSet()
        val beaconsInLine = sensorsAndBeacons.filter { (it.second.y == y) }.map { it.second }.toSet()
        return coverage.size.toLong() - sensorsInLine.size - beaconsInLine.size
    }

    fun part2(max: Int): Long {
        for (y in 0..max) {
            val coverage = mutableSetOf<IntRange>()
            for ((sensor, beacon) in sensorsAndBeacons) {
                val radius = sensor distanceTo beacon
                val distance = abs(sensor.y - y)
                if (distance > radius)
                    continue
                val xRange = (sensor.x - radius + distance)..(sensor.x + radius - distance)
                coverage.add(xRange)
            }
            for (range in coverage) {
                if ((range.last < max) && coverage.all { (it.last <= range.last) || (it.first > (range.last + 1)) })
                    return (4000000L * (range.last + 1)) + y
                if ((range.first > 0) && coverage.all { (it.first >= range.first) || (it.last < (range.first - 1)) })
                    return (4000000L * (range.first - 1)) + y
            }
        }
        return -1
    }
}

fun main() {
    val day15 = Day15(Day15::class.java.getResource(Day15.RESOURCE)!!.readText().trim().split("\n", "\r\n"))
    println("Day15::part1 -> ${day15.part1(y = 2000000)}")
    println("Day15::part2 -> ${day15.part2(max = 4000000)}")
}
