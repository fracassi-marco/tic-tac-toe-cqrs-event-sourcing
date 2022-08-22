package domain.match

import domain.DomainEvent
import java.util.*

data class Match(val id: UUID, val events: List<DomainEvent>) {

    private val grid = Grid()
    private val newEvents = mutableListOf<DomainEvent>()

    constructor(id: UUID, player1Id: String, player2Id: String) : this(id, listOf(MatchCreatedEvent(id, player1Id, player2Id))) {
        newEvents.add(events.single())
    }

    init {
        events.forEach { applyEvent(it) }
    }

    fun markCell(row: Int, column: Int, playerId: String) {
        if (grid.isNotMarkable(row, column)) {
            throw RuntimeException()
        }
        consume(CellMarkedEvent(id, row, column, playerId))
        grid.getWinner()?.let { winner ->
            consume(MatchWonEvent(id, winner))
        }
    }

    private fun consume(event: DomainEvent) {
        newEvents.add(event)
        applyEvent(event)
    }

    private fun applyEvent(event: DomainEvent) {
        when(event) {
            is CellMarkedEvent -> grid.mark(event.row, event.column, event.playerId)
        }
    }

    fun newEvents() = newEvents
}
