package use_cases

import domain.RankingRepository
import domain.match.CellMarkedEvent
import domain.match.MatchWonEvent
import domain.ranking.RankingPosition
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

class GetRankingUseCaseTest {

    private val rankingRepository: RankingRepository = mockk(relaxed = true)

    @Test
    fun `increment marked cell counter`() {
        GetRankingUseCase(rankingRepository).handle(CellMarkedEvent(UUID.randomUUID(), 0, 0, "player1"))

        verify { rankingRepository.incrementMarks("player1") }
    }

    @Test
    fun `increment wins counter`() {
        GetRankingUseCase(rankingRepository).handle(MatchWonEvent(UUID.randomUUID(), "player1"))

        verify { rankingRepository.incrementWon("player1") }
    }

    @Test
    fun `get ranking`() {
        val positions = listOf(
            RankingPosition("player2", 2, 8),
            RankingPosition("player1", 1, 3)
        )
        every { rankingRepository.getOrdered() } returns positions

        val ranking = GetRankingUseCase(rankingRepository).process()

        assertThat(ranking).isEqualTo(positions)
    }
}