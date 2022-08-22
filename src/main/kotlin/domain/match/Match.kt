package domain.match

import domain.DomainEvent
import java.util.*

data class Match(val id: UUID, private val player1Id: String, val player2Id: String) {

    private val newEvents = mutableListOf<DomainEvent>()

    init {
        newEvents.add(MatchCreatedEvent(id, player1Id, player2Id))
    }

    private val grid = Grid()

    fun markCell(row: Int, column: Int, playerId: String) {
        if (grid.isNotMarkable(row, column)) {
            throw RuntimeException()
        }
        applyEvent(CellMarkedEvent(id, row, column, playerId))
        grid.getWinner()?.let { winner ->
            applyEvent(MatchWonEvent(id, winner))
        }
    }

    private fun applyEvent(event: DomainEvent) {
        newEvents.add(event)
        consume(event)
    }

    private fun consume(event: DomainEvent) {
        when(event) {
            is CellMarkedEvent -> grid.mark(event.row, event.column, event.playerId)
        }
    }

    fun newEvents() = newEvents
}
