package domain.board

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
}