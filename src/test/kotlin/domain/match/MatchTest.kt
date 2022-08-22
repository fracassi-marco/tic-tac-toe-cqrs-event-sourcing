package domain.match

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.RuntimeException
import java.util.*

class MatchTest {
    private val matchId = UUID.randomUUID()

    @Test
    fun `generate match created event`() {
        val match = Match(matchId, "player1Id", "player2Id")

        assertThat(match.newEvents().single()).isEqualTo(MatchCreatedEvent(matchId, "player1Id", "player2Id"))
    }

    @Test
    fun `generate sign added event`() {
        val match = Match(matchId, "player1Id", "player2Id")

        match.markCell(0, 0, "player1Id")

        assertThat(match.newEvents().last()).isEqualTo(CellMarkedEvent(matchId, 0, 0, "player1Id"))
    }

    @Test
    fun `generate match won event`() {
        val match = Match(matchId, "player1Id", "player2Id")

        match.markCell(0, 0, "player1Id")
        match.markCell(0, 1, "player1Id")
        match.markCell(0, 2, "player1Id")

        assertThat(match.newEvents().last()).isEqualTo(MatchWonEvent(matchId, "player1Id"))
    }

    @Test
    fun `can not mark already full cell`() {
        val match = Match(matchId, "player1Id", "player2Id")
        match.markCell(0, 0, "player1Id")

        assertThrows<RuntimeException> { match.markCell(0, 0, "player2Id") }
    }

    @Test
    fun `can not mark out of grid cell`() {
        val match = Match(matchId, "player1Id", "player2Id")

        assertThrows<RuntimeException> { match.markCell(0, 3, "player2Id") }
    }
}