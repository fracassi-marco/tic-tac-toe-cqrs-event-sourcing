package domain.match

import domain.DomainEvent
import java.util.*

data class MatchWonEvent(private val matchId: UUID, private val playerId: String): DomainEvent
