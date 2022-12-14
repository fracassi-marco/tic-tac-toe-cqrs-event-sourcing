package use_cases

import domain.EventBus
import domain.EventRepository
import domain.InvalidStateException
import domain.match.CellMarkedEvent
import domain.match.MarkCellCommand
import domain.match.MatchCreatedEvent
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.util.*

class MarkCellUseCaseTest {
    private val eventRepository: EventRepository = mockk(relaxed = true)
    private val eventBus: EventBus = mockk(relaxed = true)

    @Test
    fun `process create match command`() {
        val matchId = UUID.randomUUID()
        every { eventRepository.load(matchId) } returns listOf(
            MatchCreatedEvent(matchId, "player1Id", "player2Id", 0)
        )

        MarkCellUseCase(eventRepository, eventBus).process(MarkCellCommand(matchId, 1, 2, "player1Id"))

        verify { eventRepository.store(ofType(CellMarkedEvent::class), 0) }
        verify { eventBus.publish(ofType(CellMarkedEvent::class)) }
    }

    @Test
    fun `retry on conflict`() {
        val matchId = UUID.randomUUID()
        every { eventRepository.load(matchId) } throws InvalidStateException() andThen listOf(
            MatchCreatedEvent(matchId, "player1Id", "player2Id", 0)
        )

        MarkCellUseCase(eventRepository, eventBus).process(MarkCellCommand(matchId, 1, 2, "player1Id"))

        verify { eventRepository.store(ofType(CellMarkedEvent::class), 0) }
        verify { eventBus.publish(ofType(CellMarkedEvent::class)) }
    }
}