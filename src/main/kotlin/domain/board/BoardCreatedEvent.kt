package domain.board

import domain.Event

data class BoardCreatedEvent(private val boardId: String, val player1Id: String, val player2Id: String) : Event
