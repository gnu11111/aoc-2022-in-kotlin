package at.gnu.adventofcode.year2022

class Day21(input: List<String>) {

    companion object {
        const val resource = "/adventofcode/year2022/Day21.txt"
        val line = """(.*): (.*)""".toRegex()
        const val rootName = "root"
        const val humanName = "humn"
    }

    data class Monkey(val name: String, var value: Long? = null, val monkey1: String? = null,
                      val operation: Char? = null, val monkey2: String? = null)

    private val root: Monkey
    private val human: Monkey
    private val monkeys = input.map {
        val (name, rest) = line.matchEntire(it)!!.destructured
        val value = rest.toLongOrNull()
        if (value == null) {
            val (monkey1, operation, monkey2) = rest.split(" ")
            Monkey(name, null, monkey1, operation[0], monkey2)
        } else
            Monkey(name, value)
    }

    init {
        root = monkeys.getMonkey(rootName)
        human = monkeys.getMonkey(humanName)
    }


    fun part1(): Long =
        root.fillValues().value!!

    fun part2(): Long {

        root.resetValues().fillValues()
        val monkeysFromHumanToRoot = mutableListOf<Monkey>()
        var monkey = human
        while (monkey != root) {
            monkey = monkeys.first { it.monkey1 == monkey.name || it.monkey2 == monkey.name }
            monkeysFromHumanToRoot += monkey
        }

        val fix: String
        val variable: String
        if (monkeysFromHumanToRoot.last().monkey1 == monkeysFromHumanToRoot.dropLast(1).last().name) {
            fix = monkeysFromHumanToRoot.last().monkey2!!
            variable = monkeysFromHumanToRoot.last().monkey1!!
        } else {
            fix = monkeysFromHumanToRoot.last().monkey1!!
            variable = monkeysFromHumanToRoot.last().monkey2!!
        }
        val fixValue = monkeys.getMonkey(fix).value!!

        var lo = 0L
        var hi = 1_000_000_000_000_000L
        var mid = 0L
        while ((lo + 1) < hi) {
            mid = (lo + hi) / 2L
            root.resetValues().fillValues(mid)
            val value = monkeys.getMonkey(variable).value!!
            val score = fixValue - value
//          println("$mid $lo $hi $fixValue $value $score")
            if (score < 0L)
                lo = mid
            else if (score == 0L)
                break
            else
                hi = mid
        }

        // search for a slightly smaller correct solution (integer arithmetic)
        for (i in -20..0) {
            root.resetValues().fillValues(mid + i)
            val value1 = monkeys.getMonkey(root.monkey1).value
            val value2 = monkeys.getMonkey(root.monkey2).value
//            println("${mid + i} $value1 $value2")
            if (value1 == value2)
                return mid + i
        }

        return mid
    }


    private fun Monkey.resetValues(): Monkey {
        for (monkey in monkeys)
            if ((monkey.monkey1 != null) && (monkey.monkey2 != null))
                monkey.value = null
        return this
    }

    private fun Monkey.fillValues(humanValue: Long? = null): Monkey {
        while (value == null) {
            for (monkey in monkeys) {
                if (monkey.value != null)
                    continue
                val value1 = if ((monkey.monkey1 == humanName) && (humanValue != null)) humanValue else monkeys.getMonkey(monkey.monkey1).value
                val value2 = if ((monkey.monkey2 == humanName) && (humanValue != null)) humanValue else monkeys.getMonkey(monkey.monkey2).value
                if ((value1 != null) && (value2 != null))
                    monkey.value = calculateExpression(value1, monkey.operation!!, value2)
            }
        }
        return this
    }

    private fun calculateExpression(value1: Long, operation: Char, value2: Long) =
        when (operation) {
            '+' -> value1 + value2
            '-' -> value1 - value2
            '*' -> value1 * value2
            '/' -> value1 / value2
            else -> error("unknown operation $operation!")
        }

    private fun List<Monkey>.getMonkey(name: String?): Monkey =
        if (name != null) first { it.name == name } else Monkey("????")
}

fun main() {
    val input = Day21::class.java.getResource(Day21.resource)!!.readText().trim().split("\n", "\r\n")
    val day21 = Day21(input)
    println("Day21::part1 -> ${day21.part1()}")
    println("Day21::part2 -> ${day21.part2()}")
}
