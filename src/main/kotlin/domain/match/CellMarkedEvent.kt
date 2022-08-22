package domain.match

import domain.DomainEvent
import java.util.*

data class CellMarkedEvent(
    private val matchId: UUID,
    val row: Int,
    val column: Int,
    val playerId: String
) : DomainEvent
