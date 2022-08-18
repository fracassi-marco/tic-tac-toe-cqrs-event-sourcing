package domain.board

import domain.Event

data class CellMarkedEvent(
    private val boardId: String,
    val row: Int,
    val column: Int,
    val playerId: String
) : Event {
}
