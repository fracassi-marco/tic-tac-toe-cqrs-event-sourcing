package domain.match

import domain.DomainEvent
import java.util.*

data class MatchWonEvent(
    private val matchId: UUID,
    val playerId: String,
    val version: Long
) : DomainEvent(matchId, version)
