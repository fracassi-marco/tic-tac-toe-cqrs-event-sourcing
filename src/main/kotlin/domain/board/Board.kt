package domain.board

import domain.Event

data class Board(private val id: String, private val playerId: String) {

    private val newEvents = mutableListOf<Event>()

    init {
        newEvents.add(BoardCreatedEvent(id))
    }

    fun newEvents() = newEvents
}
