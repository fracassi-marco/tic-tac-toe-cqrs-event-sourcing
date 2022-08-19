package domain.match

import domain.DomainEvent

data class CellMarkedEvent(
    private val matchId: String,
    val row: Int,
    val column: Int,
    val playerId: String
) : DomainEvent
