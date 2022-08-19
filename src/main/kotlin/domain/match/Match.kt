package domain.match

import domain.DomainEvent

data class Match(private val id: String, private val player1Id: String, val player2Id: String) {

    private val newEvents = mutableListOf<DomainEvent>()

    init {
        newEvents.add(MatchCreatedEvent(id, player1Id, player2Id))
    }

    private val grid = Grid()

    fun markCell(row: Int, column: Int, playerId: String) {
        if (grid.isNotMarkable(row, column)) {
            throw RuntimeException()
        }
        val event = CellMarkedEvent(id, row, column, playerId)
        newEvents.add(event)
        consume(event)
        grid.getWinner()?.let { winner ->
            val matchWonEvent = MatchWonEvent(id, winner)
            newEvents.add(matchWonEvent)
            consume(matchWonEvent)
        }
    }

    private fun consume(event: DomainEvent) {
        when(event) {
            is CellMarkedEvent -> grid.mark(event.row, event.column, event.playerId)
        }
    }

    fun newEvents() = newEvents
}
