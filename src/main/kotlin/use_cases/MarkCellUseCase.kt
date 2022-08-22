package use_cases

import domain.EventBus
import domain.EventRepository
import domain.match.MarkCellCommand
import domain.match.Match

class MarkCellUseCase(private val eventRepository: EventRepository, private val eventBus: EventBus) {
    fun process(command: MarkCellCommand): Match {
        val events = eventRepository.load(command.matchId)
        val match = Match(command.matchId, events)
        match.markCell(command.row, command.column, command.playerId)
        match.newEvents().forEach {
            eventRepository.store(it)
            eventBus.post(it)
        }
        return match
    }
}