package at.gnu.adventofcode.year2022

class Day13(input: List<Pair<String, String>>) {

    companion object {
        const val resource = "/adventofcode/year2022/Day13.txt"
        val divider1 = Packet(listOf(Value(2)))
        val divider2 = Packet(listOf(Value(6)))
    }

    private val packets = input.map { Packet.of(it.first) to Packet.of(it.second) }


    fun part1(): Int =
        packets.foldIndexed(0) { i, sum, packet -> sum + if (packet.first < packet.second) i + 1 else 0 }

    fun part2(): Int {
        val sortedPackets = (packets.flatMap { listOf(it.first, it.second) } + divider1 + divider2).sorted()
        return (sortedPackets.indexOf(divider1) + 1) * (sortedPackets.indexOf(divider2) + 1)
    }


    sealed class Element
    data class Value(val value: Int) : Element()
    data class Packet(val values: List<Element> = emptyList()): Element(), Comparable<Packet> {

        companion object {

            fun of(text: String): Packet {
                val values = mutableListOf<Element>()
                var pos = 1
                while (pos < text.length) {
                    when (text[pos]) {
                        '[' -> {
                            val end = text.indexOfMatchingBracket(pos)
                            values.add(of(text.substring(pos, end + 1)))
                            pos = end + 2
                        }
                        in ('0'..'9') -> {
                            var end = text.indexOf(',', pos)
                            if (end < 0) end = text.indexOfMatchingBracket(pos)
                            values.add(Value(text.substring(pos, end).toInt()))
                            pos = end + 1
                        }
                        else -> pos++
                    }
                }
                return Packet(values)
            }

            private fun String.indexOfMatchingBracket(pos: Int): Int {
                var open = 1
                var i = pos
                while (++i < this.length)
                    if (this[i] == '[')
                        open++
                    else if (this[i] == ']')
                        if (open == 1) return i else open--
                return -1
            }
        }

        override fun compareTo(other: Packet): Int {
            for (i in this.values.indices) {
                if (i >= other.values.size) return 1
                val thisElement = this.values[i]
                val otherElement = other.values[i]
                val result = when {
                    (thisElement is Value) && (otherElement is Value) ->
                        thisElement.value.compareTo(otherElement.value)
                    (thisElement is Packet) && (otherElement is Packet) ->
                        thisElement.compareTo(otherElement)
                    (thisElement is Value) && (otherElement is Packet) ->
                        Packet(listOf(thisElement)).compareTo(otherElement)
                    (thisElement is Packet) && (otherElement is Value) ->
                        thisElement.compareTo(Packet(listOf(otherElement)))
                    else ->
                        error("unable to compare $this with $other")
                }
                if (result != 0)
                    return result
            }
            return if (other.values.size > this.values.size) -1 else 0
        }
    }
}

fun main() {
    val packets = Day13::class.java.getResource(Day13.resource)!!.readText().trim().replace("\r", "")
        .replace("\n\n", "\n").split("\n").chunked(2).map { it[0] to it[1] }
    val day13 = Day13(packets)
    println("Day13::part1 -> ${day13.part1()}")
    println("Day13::part2 -> ${day13.part2()}")
}
