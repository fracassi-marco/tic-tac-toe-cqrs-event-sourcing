package domain.board

import domain.Event

data class BoardCreatedEvent(private val boardId: String) : Event
