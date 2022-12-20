package at.gnu.adventofcode.year2022

class Day03(private val rucksacks: List<String>) {

    companion object {
        const val resource = "/adventofcode/year2022/Day03.txt"
    }

    fun part1(): Int =
        rucksacks.fold(0) { sum, rucksack ->
            val (compartment1, compartment2) = rucksack.splitIntoCompartements()
            sum + commonItem(compartment1, compartment2).priority()
        }

    fun part2(): Int =
        rucksacks.chunked(3).fold(0) { sum, group ->
            sum + commonItem(group[0], group[1], group[2]).priority()
        }

    private fun String.splitIntoCompartements(): Pair<String, String> =
        Pair(substring(0, length / 2), substring(length / 2))

    private fun commonItem(vararg contents: String): Char {
        outer@for (c in contents[0]) {
            for (content in contents.drop(1))
                if (!content.contains(c))
                    continue@outer
            return c
        }
        return ' '
    }

    private fun Char.priority(): Int =
        if (isLowerCase())
            this - 'a' + 1
        else
            this - 'A' + 27
}

fun main() {
    val input = Day03::class.java.getResource(Day03.resource)!!.readText().trim().split("\n", "\r\n")
    val day03 = Day03(input)
    println("Day03::part1 -> ${day03.part1()}")
    println("Day03::part2 -> ${day03.part2()}")
}
