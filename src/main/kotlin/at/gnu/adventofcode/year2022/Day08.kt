package at.gnu.adventofcode.year2022

class Day08(private val trees: List<String>) {

    companion object {
        const val input = "/adventofcode/year2022/Day08.txt"
    }

    fun part1(): Int =
        trees.foldIndexed(0) { y, sum, row ->
            sum + row.foldIndexed(0) { x, sumInRow, _ ->
                sumInRow + if (isBorder(x, y) || isVisible(x, y)) 1 else 0
            }
        }

    fun part2(): Int {
        var max = 0
        for (y in trees.indices)
            for (x in trees.first().indices)
                if (!isBorder(x, y))
                    max = max.coerceAtLeast(countVisible(x, y))
        return max
    }

    private fun isBorder(x: Int, y: Int): Boolean =
        ((x == 0) || (x == (trees.first().length - 1)) || (y == 0) || (y == (trees.size - 1)))

    private fun isVisible(x: Int, y: Int): Boolean {
        val height = trees[y][x]
        var visible = true
        for (i in 0 until x)
            if (trees[y][i] >= height)
                visible = false
        if (visible)
            return true
        else
            visible = true
        for (i in (x + 1) until trees.first().length)
            if (trees[y][i] >= height)
                visible = false
        if (visible)
            return true
        else
            visible = true
        for (i in 0 until y)
            if (trees[i][x] >= height)
                visible = false
        if (visible)
            return true
        else
            visible = true
        for (i in (y + 1) until trees.size)
            if (trees[i][x] >= height)
                visible = false
        return visible
    }

    private fun countVisible(x: Int, y: Int): Int {
        val height = trees[y][x]
        var scenicScore = 1
        var visible = 0
        for (i in (x - 1) downTo 0) {
            visible++
            if (trees[y][i] >= height)
                break
        }
        scenicScore *= visible
        visible = 0
        for (i in (x + 1) until trees.first().length) {
            visible++
            if (trees[y][i] >= height)
                break
        }
        scenicScore *= visible
        visible = 0
        for (i in (y - 1) downTo 0) {
            visible++
            if (trees[i][x] >= height)
                break
        }
        scenicScore *= visible
        visible = 0
        for (i in (y + 1) until trees.size) {
            visible++
            if (trees[i][x] >= height)
                break
        }
        return scenicScore * visible
    }
}

fun main() {
    val day08 = Day08(Day08::class.java.getResource(Day08.input)!!.readText().trim().split("\n", "\r\n"))
    println("Day08::part1 -> ${day08.part1()}")
    println("Day08::part2 -> ${day08.part2()}")
}
