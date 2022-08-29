package domain.match

import domain.DomainEvent
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.*

class CellMarkedEvent(
    private val matchId: UUID,
    val row: Int,
    val column: Int,
    val playerId: String,
    val version: Long,
) : DomainEvent(matchId, version)
