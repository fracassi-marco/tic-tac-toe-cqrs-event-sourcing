package domain.match

import domain.DomainEvent

data class MatchWonEvent(private val matchId: String, private val playerId: String): DomainEvent
