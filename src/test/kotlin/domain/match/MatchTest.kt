package domain.match

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class MatchTest {
    private val matchId = UUID.randomUUID()

    @Test
    fun `generate match created event`() {
        val match = Match(matchId, "player1Id", "player2Id")

        val event = match.newEvents().single() as MatchCreatedEvent
        assertThat(event.player1Id).isEqualTo("player1Id")
        assertThat(event.player2Id).isEqualTo("player2Id")
    }

    @Test
    fun `generate sign added event`() {
        val match = Match(matchId, "player1Id", "player2Id")

        match.markCell(0, 0, "player1Id")

        val event = match.newEvents().last() as CellMarkedEvent
        assertThat(event.row).isEqualTo(0)
        assertThat(event.column).isEqualTo(0)
        assertThat(event.playerId).isEqualTo("player1Id")
    }

    @Test
    fun `generate match won event`() {
        val match = Match(matchId, "player1Id", "player2Id")

        match.markCell(0, 0, "player1Id")
        match.markCell(0, 1, "player1Id")
        match.markCell(0, 2, "player1Id")

        val event = match.newEvents().last() as MatchWonEvent
        assertThat(event.playerId).isEqualTo("player1Id")
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

    @Test
    fun `can not mark cell when match in already won`() {
        val match = Match(matchId, "player1Id", "player2Id")

        match.markCell(0, 0, "player1Id")
        match.markCell(0, 1, "player1Id")
        match.markCell(0, 2, "player1Id")

        assertThrows<RuntimeException> { match.markCell(1, 1, "player2Id") }
    }

    @Test
    fun `build from events`() {
        val match = Match(
            matchId, listOf(
                MatchCreatedEvent(matchId, "player1Id", "player2Id", 0),
                CellMarkedEvent(matchId, 0, 0, "player1Id", 1)
            )
        )

        assertThat(match.version).isEqualTo(1)
        assertThrows<RuntimeException> { match.markCell(0, 0, "player2Id") }
    }
}