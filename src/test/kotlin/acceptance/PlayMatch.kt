package acceptance

import domain.match.CreateMatchCommand
import domain.match.MarkCellCommand
import domain.match.Match
import infrastructure.EventStore
import infrastructure.InMemoryRankingRepository
import infrastructure.SyncEventBus
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import use_cases.CreateMatchUseCase
import use_cases.GetRankingUseCase
import use_cases.MarkCellUseCase
import kotlin.random.Random
import kotlin.random.nextInt

class PlayMatch {

    private val eventRepository = EventStore()
    private val rankingRepository = InMemoryRankingRepository()
    private val getRankingUseCase = GetRankingUseCase(rankingRepository)
    private val eventBus = SyncEventBus().apply { register(getRankingUseCase) }
    private val createMatchUseCase = CreateMatchUseCase(eventRepository, eventBus)
    private val markCellUseCase = MarkCellUseCase(eventRepository, eventBus)

    @Test
    fun `mario wins the match`() {
        val match = createMatchUseCase.process(CreateMatchCommand("mario", "gianni"))
        markCellUseCase.process(MarkCellCommand(match.id, 0, 0, "mario"))
        markCellUseCase.process(MarkCellCommand(match.id, 1, 0, "gianni"))
        markCellUseCase.process(MarkCellCommand(match.id, 1, 1, "mario"))
        markCellUseCase.process(MarkCellCommand(match.id, 0, 1, "gianni"))
        markCellUseCase.process(MarkCellCommand(match.id, 2, 2, "mario"))

        val ranking = getRankingUseCase.process()
        assertThat(ranking.first().playerId).isEqualTo("mario")
        assertThat(ranking.last().playerId).isEqualTo("gianni")

        ranking.forEach { println(it) }
    }

    @Test
    fun `multi match`() {
        val players = listOf("mario", "gianni", "rosa", "anna", "andrea")

        runBlocking {
            val matches = List(1500) {
                launch {
                    val shuffledPlayers = players.shuffled()
                    val player1Id = shuffledPlayers[0]
                    val player2Id = shuffledPlayers[1]
                    val match = createMatchUseCase.process(CreateMatchCommand(player1Id, player2Id))
                    List(20) { launch { playMatch(match, player1Id, player2Id) } }
                }
            }
            matches.forEach { it.join() }
        }

        val ranking = getRankingUseCase.process()
        ranking.forEach { println(it) }

        assertThat(ranking.sumOf { it.wins }).isLessThanOrEqualTo(1500)
    }

    private fun playMatch(match: Match, player1Id: String, player2Id: String) {
        try {
            markCellUseCase.process(MarkCellCommand(match.id, Random.nextInt(0..2), Random.nextInt(0..2), player1Id))
            markCellUseCase.process(MarkCellCommand(match.id, Random.nextInt(0..2), Random.nextInt(0..2), player2Id))
        } catch (e: Throwable) {
        }
    }
}