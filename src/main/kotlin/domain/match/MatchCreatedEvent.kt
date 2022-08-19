package domain.match

import domain.Event

data class MatchCreatedEvent(private val matchId: String, val player1Id: String, val player2Id: String) : Event
