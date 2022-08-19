package domain.match

import domain.Event

data class MatchWonEvent(private val matchId: String, private val playerId: String): Event
