package use_cases

import domain.EventBus
import domain.EventRepository
import domain.match.CreateMatchCommand
import domain.match.Match
import java.util.UUID.randomUUID

class CreateMatchUseCase(private val eventRepository: EventRepository, private val eventBus: EventBus) {
    fun process(command: CreateMatchCommand): Match {
        val match = Match(randomUUID(), command.player1Id, command.player2Id)
        match.newEvents().forEach {
            eventRepository.store(it)
            eventBus.publish(it)
        }
        return match
    }
}
