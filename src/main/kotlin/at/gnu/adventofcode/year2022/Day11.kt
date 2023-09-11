package at.gnu.adventofcode.year2022

class Day11(input: List<String>) {

    companion object {
        const val RESOURCE = "/adventofcode/year2022/Day11.txt"
        val section = """
            Monkey (\d+):
              Starting items: (.*)
              Operation: new = (.*)
              Test: divisible by (\d+)
                If true: throw to monkey (\d+)
                If false: throw to monkey (\d+)
            """.trimIndent().toRegex()
    }

    data class Operation(val arg1: String, val op: Char, val arg2: String)

    data class Monkey(
        val number: Int,
        val items: MutableList<Long>,
        val operation: Operation,
        val divisor: Long,
        val destination: Pair<Int, Int>
    ) {

        fun inspect(item: Long): Long {
            val arg1 = operation.arg1.toLongOrNull() ?: item
            val arg2 = operation.arg2.toLongOrNull() ?: item
            return when (operation.op) {
                '+' -> arg1 + arg2
                '*' -> arg1 * arg2
                else -> error("unknown operation '${operation.op}'!")
            }
        }
    }

    private val monkeys = input.map {
        val values = section.matchEntire(it)!!.groupValues
        val number = values[1].toInt()
        val items = values[2].split(",").map { item -> item.trim().toLong() }.toMutableList()
        val (arg1, op, arg2) = values[3].split(" ")
        val operation = Operation(arg1, op[0], arg2)
        val divisor = values[4].toLong()
        val destination = Pair(values[5].toInt(), values[6].toInt())
        Monkey(number, items, operation, divisor, destination)
    }.sortedBy { it.number }

    fun part1(): Long =
        createNewMonkeysList().calculateInspections(20) { it / 3 }.calculateMonkeyBusiness()

    fun part2(): Long {
        // Chinese Remainder Theorem (multiplication is sufficient, because the divisors are all prime!)
        val allDivisorsMultiplied = monkeys.map { it.divisor }.reduce { acc, it -> acc * it }
        val inspections = createNewMonkeysList().calculateInspections(10_000) { it % allDivisorsMultiplied }
        return inspections.calculateMonkeyBusiness()
    }

    private fun createNewMonkeysList(): List<Monkey> =
        monkeys.map { it.copy(items = it.items.toMutableList()) }

    private fun MutableMap<Int, Long>.calculateMonkeyBusiness(): Long {
        val mostActiveMonkeyInspections = values.sortedDescending().take(2)
        return mostActiveMonkeyInspections.first() * mostActiveMonkeyInspections.last()
    }

    private fun List<Monkey>.calculateInspections(rounds: Int, postOperation: (Long) -> Long): MutableMap<Int, Long> {
        val inspections = mutableMapOf<Int, Long>()
        for (round in 1..rounds) {
            for (monkey in this) {
                val itemsThrown = mutableListOf<Triple<Long, Long, Monkey>>()
                for (item in monkey.items) {
                    inspections[monkey.number] = inspections.getOrPut(monkey.number) { 0L }.plus(1L)
                    val newWorryLevel = postOperation(monkey.inspect(item))
                    itemsThrown += if ((newWorryLevel % monkey.divisor) == 0L)
                        Triple(item, newWorryLevel, getByNumber(monkey.destination.first))
                    else
                        Triple(item, newWorryLevel, getByNumber(monkey.destination.second))
                }
                for (itemThrown in itemsThrown) {
                    monkey.items.remove(itemThrown.first)
                    itemThrown.third.items.add(itemThrown.second)
                }
            }
        }
        return inspections
    }

    private fun List<Monkey>.getByNumber(number: Int): Monkey =
        this.firstOrNull { it.number == number } ?: error("no such monkey with number $number!")
}

fun main() {
    val day11 = Day11(Day11::class.java.getResource(Day11.RESOURCE)!!.readText().trim().replace("\r", "").split("\n\n"))
    println("Day11::part1 -> ${day11.part1()}")
    println("Day11::part2 -> ${day11.part2()}")
}
