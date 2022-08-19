package domain.match

import domain.DomainEvent

data class MatchCreatedEvent(private val matchId: String, val player1Id: String, val player2Id: String) : DomainEvent
