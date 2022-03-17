package chess

fun String.row(): Int = when(this.last().toString()) {
    "8" -> 0
    "7" -> 1
    "6" -> 2
    "5" -> 3
    "4" -> 4
    "3" -> 5
    "2" -> 6
    "1" -> 7
    else -> -1
}

fun String.col(): Int = when(this.first().toString()) {
    "a" -> 0
    "b" -> 1
    "c" -> 2
    "d" -> 3
    "e" -> 4
    "f" -> 5
    "g" -> 6
    "h" -> 7
    else -> -1
}

fun row2rank(row: Int) = 8 - row

fun col2file(col: Int) = when(col) {
    0 -> "a"
    1 -> "b"
    2 -> "c"
    3 -> "d"
    4 -> "e"
    5 -> "f"
    6 -> "g"
    7 -> "h"
    else -> ""
}

class Table() {
    val data = mutableListOf<MutableList<String>>(
        mutableListOf(" ", " ", " ", " ", " ", " ", " ", " "),
        mutableListOf("B", "B", "B", "B", "B", "B", "B", "B"),
        mutableListOf(" ", " ", " ", " ", " ", " ", " ", " "),
        mutableListOf(" ", " ", " ", " ", " ", " ", " ", " "),
        mutableListOf(" ", " ", " ", " ", " ", " ", " ", " "),
        mutableListOf(" ", " ", " ", " ", " ", " ", " ", " "),
        mutableListOf("W", "W", "W", "W", "W", "W", "W", "W"),
        mutableListOf(" ", " ", " ", " ", " ", " ", " ", " "),
    )

    fun printTable() {
        val line = "  +---+---+---+---+---+---+---+---+"
        repeat(8) { row ->
            println(line)
            print("${row2rank(row)} |")
            repeat(8) { col ->
                print(" ${data[row][col].uppercase()} |")
            }
            println("")
        }
        println(line)
        print(" ")
        repeat(8) { col ->
            print("   ${col2file(col)}")
        }
        println("")
    }

    fun movePawn(row1: Int, col1: Int, row2: Int, col2: Int) {
        data[row2][col2] = data[row1][col1].lowercase()
        data[row1][col1] = " "
    }
}

fun inputStep(name: String, color: String): String {
    while(true) {
        println("${name}'s turn:")
        val s = readLine()!!
        if (s == "exit") return s
        if (s.matches(Regex("[a-h][1-8][a-h][1-8]"))) {
            val start = s.substring(0,2)
            val stop = s.substring(2,4)
            val row1 = start.row()
            val col1 = start.col()
            val row2 = stop.row()
            val col2 = stop.col()
            val cell1 = table.data[row1][col1]
            val cell2 = table.data[row2][col2]
            if (col1 != col2) {
                println("Invalid Input")
                continue
            }
            if (color == "B") {
                if (cell1 == "B" && cell2 == " " && 0 < row2 - row1 && row2 - row1 < 3) {
                    table.movePawn(row1, col1, row2, col2)
                    return s
                }
                if (cell1 == "b" && cell2 == " " && 0 < row2 - row1 && row2 - row1 < 2) {
                    table.movePawn(row1, col1, row2, col2)
                    return s
                }
                if (cell1 == "W" || cell1 == "w" || cell1 == " ") {
                    println("No black pawn at $start")
                    continue
                }
            }
            if (color == "W") {
                if (cell1 == "W" && cell2 == " " && 0 < row1 - row2 && row1 - row2 < 3) {
                    table.movePawn(row1, col1, row2, col2)
                    return s
                }
                if (cell1 == "w" && cell2 == " " && 0 < row1 - row2 && row1 - row2 < 2) {
                    table.movePawn(row1, col1, row2, col2)
                    return s
                }
                if (cell1 == "B" || cell1 == "b" || cell1 == " ") {
                    println("No white pawn at $start")
                    continue
                }
            }
        }
        println("Invalid Input")
    }
}

val table = Table()

fun main() {
    println("Pawns-Only Chess")
    println("First Player's name:")
    val name1 = readLine()!!
    println("Second Player's name:")
    val name2 = readLine()!!
    table.printTable()

    while(true) {
        val step1 = inputStep(name1, "W")
        if (step1 == "exit") break

        table.printTable()

        val step2 = inputStep(name2, "B")
        if (step2 == "exit") break

        table.printTable()
    }

    println("Bye!")
}