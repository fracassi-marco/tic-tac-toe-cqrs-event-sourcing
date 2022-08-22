package domain.match

import domain.DomainEvent
import java.util.*

data class MatchCreatedEvent(private val matchId: UUID, val player1Id: String, val player2Id: String) : DomainEvent
