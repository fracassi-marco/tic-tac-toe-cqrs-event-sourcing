package use_cases

import domain.EventBus
import domain.EventRepository
import domain.match.CreateMatchCommand
import domain.match.Match
import domain.match.MatchCreatedEvent
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class CreateMatchUseCaseTest {

    private val eventRepository: EventRepository = mockk(relaxed = true)
    private val eventBus: EventBus = mockk(relaxed = true)

    @Test
    fun `process create match command`() {
        val command = CreateMatchCommand("player1Id", "player2Id")

        val match: Match = CreateMatchUseCase(eventRepository, eventBus).process(command)

        val event = MatchCreatedEvent(match.id, "player1Id", "player2Id")
        verify { eventRepository.store(event) }
        verify { eventBus.post(event) }
    }
}