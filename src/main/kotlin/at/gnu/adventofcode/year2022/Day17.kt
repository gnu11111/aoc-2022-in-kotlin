package at.gnu.adventofcode.year2022

class Day17(private val jetPattern: String) {

    companion object {
        const val RESOURCE = "/adventofcode/year2022/Day17.txt"
        const val WIDTH = 7
        val shapes = listOf(listOf("####"), listOf(".#.", "###", ".#."), listOf("..#", "..#", "###"),
            listOf("#", "#", "#", "#"), listOf("##", "##"))
    }

    data class Signature(val ground: Int, val shapeNumber: Int, val jetStreamCycle: Int)


    fun part1(): Long =
        simulateRockDrops(2022L)

    fun part2(): Long =
        simulateRockDrops(1000000000000L)


    private fun simulateRockDrops(rocks: Long): Long {
        val ground = mutableListOf("#".repeat(WIDTH).toCharArray())
        val seenSignatures = mutableMapOf<Signature, Pair<Long, Long>>()
        var jetStreamCycle = -1
        var rock = -1L
        var height = 0L
        var addedHeight = 0L
        while (++rock < rocks) {
            val shapeNumber = (rock % shapes.size).toInt()
            val shape = shapes[shapeNumber]
            var y = ground.size + shape.size + 3L
            var x = 2L
            while (!ground.hits(shape, x, y)) {
                jetStreamCycle = (jetStreamCycle + 1) % jetPattern.length
                val direction = jetPattern[jetStreamCycle]
                val dx = if ((direction == '>') && (x < (WIDTH - shape.first().length)))
                    1L
                else if ((direction == '<') && (x > 0))
                    -1L
                else
                    0L
                if (!ground.hits(shape, x + dx, y))
                    x += dx
                y--
            }
            height += ground.addRock(shape, x, y + 1L)
            val signature = Signature(ground.takeLast(10).joinToString { it.joinToString("") }.hashCode(),
                shapeNumber, jetStreamCycle)
            if (!seenSignatures.containsKey(signature)) {
                seenSignatures[signature] = rock to height
            } else {
                val (oldRock, oldHeight) = seenSignatures[signature]!!
                val loopSize = rock - oldRock
                val loopHeight = height - oldHeight
                println("loop detected, rock: $rock, height: $height, loopSize: $loopSize, loopHeight: $loopHeight")
                val times = ((rocks - rock) / loopSize)
                rock += times * loopSize
                addedHeight = times * loopHeight
                seenSignatures.clear()
            }
        }
        return height + addedHeight
    }

    private fun List<CharArray>.hits(shape: List<String>, x: Long, y: Long): Boolean {
        for (dy in shape.size downTo 1) {
            if ((y - dy) >= this.size)
                continue
            for (dx in shape[dy - 1].indices) {
                val shapeChar = shape[dy - 1][dx]
                val groundChar = this[(y - dy).toInt()][(x + dx).toInt()]
                if ((shapeChar == '#') && (groundChar == '#'))
                    return true
            }
        }
        return false
    }

    private fun MutableList<CharArray>.addRock(shape: List<String>, x: Long, y: Long): Int {
        var addedHeight = 0
        for (dy in shape.size downTo 1) {
            if ((y - dy) >= this.size) {
                this.add(".".repeat(WIDTH).toCharArray())
                addedHeight++
            }
            for (dx in shape[dy - 1].indices)
                this[(y - dy).toInt()][(x + dx).toInt()] = shape[dy - 1][dx]
        }
        return addedHeight
    }
}

fun main() {
    val day17 = Day17(Day17::class.java.getResource(Day17.RESOURCE)!!.readText().trim())
    println("Day17::part1 -> ${day17.part1()}")
    println("Day17::part2 -> ${day17.part2()}")
}
