package at.gnu.adventofcode.year2022

class Day07(input: List<String>) {

    companion object {
        const val input = "/adventofcode/year2022/Day07.txt"
        val cd = """\$ cd (.*)""".toRegex()
        val dir = """dir (.*)""".toRegex()
        val file = """(\d+) (.*)""".toRegex()
    }

    data class File(val name: String, val size: Int = 0)

    data class Directory(
        val name: String,
        val parent: Directory? = null,
        val files: MutableList<File> = mutableListOf(),
        val directories: MutableList<Directory> = mutableListOf()
    )

    private val sizes = createTree(input).calculateDirectorySizes()

    fun part1(): Int =
        sizes.sumOf { if (it < 100000) it else 0 }

    fun part2(): Int =
        sizes.sorted().first { it > (sizes.first() - 40000000) }

    private fun Directory.calculateDirectorySizes(sizes: List<Int> = emptyList()): List<Int> =
        directories.fold(sizes + calculateTotalSize()) { acc, it -> acc + it.calculateDirectorySizes() }

    private fun Directory.calculateTotalSize(): Int =
        files.sumOf { it.size } + directories.sumOf { it.calculateTotalSize() }

    private fun createTree(input: List<String>): Directory {
        val root = Directory("/")
        var current = root
        for (line in input) {
            when {
                cd.matches(line) -> current = when (val name = cd.matchEntire(line)!!.groups[1]!!.value) {
                    "/" -> root
                    ".." -> current.parent ?: root
                    else -> current.directories.firstOrNull { it.name == name } ?: Directory(name, current)
                }

                dir.matches(line) -> {
                    val directoryName = dir.matchEntire(line)!!.groups[1]!!.value
                    if (current.directories.none { it.name == directoryName })
                        current.directories += Directory(directoryName, current)
                }

                file.matches(line) -> {
                    val fileName = file.matchEntire(line)!!.groups[2]!!.value
                    if (current.files.none { it.name == fileName })
                        current.files += File(fileName, size = file.matchEntire(line)!!.groups[1]!!.value.toInt())
                }

                else -> continue
            }
        }
        return root
    }
}

fun main() {
    val day07 = Day07(Day07::class.java.getResource(Day07.input)!!.readText().trim().split("\n", "\r\n"))
    println("Day07::part1 -> ${day07.part1()}")
    println("Day07::part2 -> ${day07.part2()}")
}
