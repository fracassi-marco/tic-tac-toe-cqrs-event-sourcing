package domain.match

import domain.DomainEvent
import java.util.*

data class Match(val id: UUID, val events: List<DomainEvent>) {

    var version = 0L
    private var alreadyWon = false
    private val grid = Grid()
    private val newEvents = mutableListOf<DomainEvent>()

    constructor(id: UUID, player1Id: String, player2Id: String) : this(id, listOf(MatchCreatedEvent(id, player1Id, player2Id, 0))) {
        newEvents.add(events.single())
    }

    init {
        events.forEach {
            applyEvent(it)
            version = it.aggregateVersion
        }
    }

    fun markCell(row: Int, column: Int, playerId: String) {
        if(alreadyWon) {
            throw RuntimeException()
        }
        if (grid.isNotMarkable(row, column)) {
            throw RuntimeException()
        }
        consume(CellMarkedEvent(id, row, column, playerId, nextVersion()))
        grid.getWinner()?.let { winner ->
            consume(MatchWonEvent(id, winner, nextVersion()))
        }
    }

    private fun nextVersion() = newEvents().size + 1L

    private fun consume(event: DomainEvent) {
        newEvents.add(event)
        applyEvent(event)
    }

    private fun applyEvent(event: DomainEvent) {
        when(event) {
            is CellMarkedEvent -> grid.mark(event.row, event.column, event.playerId)
            is MatchWonEvent -> alreadyWon = true
        }
    }

    fun newEvents() = newEvents
}
