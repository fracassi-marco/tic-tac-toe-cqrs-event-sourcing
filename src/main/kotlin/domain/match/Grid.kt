package domain.match

class Grid {
    private val cells = listOf(
        mutableListOf<String?>(null, null, null),
        mutableListOf<String?>(null, null, null),
        mutableListOf<String?>(null, null, null)
    )

    fun isNotMarkable(row: Int, column: Int) = isOutOfBounds(row, column) || isNotEmpty(row, column)

    fun mark(row: Int, column: Int, marker: String) {
        cells[row][column] = marker
    }

    private fun isNotEmpty(row: Int, column: Int) = cells[row][column] != null

    private fun isOutOfBounds(row: Int, column: Int) = (row < 0 || row > cells.size
            || column < 0 || column > cells[row].size)

    fun getWinner(): String? {
        (0 until 2).forEach {
            if(cells[it][0] == cells[it][1] && cells[it][0] == cells[it][2])
                return cells[it][0]

            if(cells[0][it] == cells[1][it] && cells[0][it] == cells[2][it])
                return cells[0][it]
        }
        if(cells[0][0] == cells[1][1] && cells[0][0] == cells[2][2])
            return cells[0][0]
        if(cells[0][2] == cells[1][1] && cells[0][2] == cells[2][0])
            return cells[0][2]

        return null
    }
}