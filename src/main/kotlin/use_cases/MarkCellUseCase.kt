package use_cases

import domain.EventBus
import domain.EventRepository
import domain.InvalidStateException
import domain.match.MarkCellCommand
import domain.match.Match
import use_cases.Retry.retry

class MarkCellUseCase(private val eventRepository: EventRepository, private val eventBus: EventBus) {

    fun process(command: MarkCellCommand): Match {
        retry(3, InvalidStateException::class.java) {
            val events = eventRepository.load(command.matchId)
            val match = Match(command.matchId, events)
            match.markCell(command.row, command.column, command.playerId)
            match.newEvents().forEach {
                eventRepository.store(it, match.version)
                eventBus.publish(it)
            }
            return match
        }
    }
}

