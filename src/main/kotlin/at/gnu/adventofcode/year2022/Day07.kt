package at.gnu.adventofcode.year2022

class Day07(private val input: List<String>) {

    companion object {
        const val input = "/adventofcode/year2022/Day07.txt"
        val cd = """\$ cd (.*)""".toRegex()
        val dir = """dir (.*)""".toRegex()
        val file = """(\d+) (.*)""".toRegex()
    }

    data class File(val name: String, val size: Int)
    data class Directory(val name: String, val files: MutableList<File>, val directories: MutableList<Directory>,
                         val parent: Directory?)

    private val sizes = createTree().calculateTotalDirectorySizes()

    fun part1(): Int =
        sizes.sumOf { if (it < 100000) it else 0 }

    fun part2(): Int =
        sizes.sorted().first { it > (sizes.first() - 40000000) }

    private fun Directory.calculateTotalDirectorySizes(sizes: List<Int> = emptyList()): List<Int> =
        directories.fold(sizes + calculateTotalSize()) { acc, it -> acc + it.calculateTotalDirectorySizes() }

    private fun Directory.calculateTotalSize(): Int =
        files.sumOf { it.size } + directories.sumOf { it.calculateTotalSize() }

    private fun createTree(): Directory {
        val root = Directory("/", mutableListOf(), mutableListOf(), null)
        var current = root
        for (line in input) {
            when {
                cd.matches(line) -> {
                    current = when (val directoryName = cd.matchEntire(line)!!.groups[1]!!.value) {
                        "/" -> root
                        ".." -> current.parent ?: root
                        else -> {
                            val directory = current.directories.firstOrNull { it.name == directoryName }
                            directory ?: Directory(directoryName, mutableListOf(), mutableListOf(), current)
                        }
                    }
                }

                dir.matches(line) -> {
                    val directoryName = dir.matchEntire(line)!!.groups[1]!!.value
                    val directory = current.directories.firstOrNull { it.name == directoryName }
                    if (directory == null)
                        current.directories += Directory(directoryName, mutableListOf(), mutableListOf(), current)
                }

                file.matches(line) -> {
                    val fileSize = file.matchEntire(line)!!.groups[1]!!.value.toInt()
                    val fileName = file.matchEntire(line)!!.groups[2]!!.value
                    val file1 = current.files.firstOrNull { it.name == fileName }
                    if (file1 == null)
                        current.files += File(fileName, fileSize)
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
