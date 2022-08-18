package domain.board

import domain.Event

data class Board(private val id: String, private val player1Id: String, val player2Id: String) {

    private val newEvents = mutableListOf<Event>()
    init {
        newEvents.add(BoardCreatedEvent(id, player1Id, player2Id))
    }

    private val grid = listOf(
        mutableListOf<String?>(null, null, null),
        mutableListOf<String?>(null, null, null),
        mutableListOf<String?>(null, null, null)
    )

    fun markCell(row: Int, column: Int, playerId: String) {
        if(grid[row][column] != null) {
            throw RuntimeException()
        }
        val event = CellMarkedEvent(id, row, column, playerId)
        newEvents.add(event)
        consume(event)
    }

    private fun consume(event: CellMarkedEvent) {
        grid[event.row][event.column] = event.playerId
    }

    fun newEvents() = newEvents
}
