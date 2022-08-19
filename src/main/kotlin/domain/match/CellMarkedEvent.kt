package domain.match

import domain.Event

data class CellMarkedEvent(
    private val matchId: String,
    val row: Int,
    val column: Int,
    val playerId: String
) : Event
