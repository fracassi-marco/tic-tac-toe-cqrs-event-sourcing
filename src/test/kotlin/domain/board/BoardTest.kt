package domain.board

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `generate new board event`() {
        val board = Board("boardId", "playerId")

        assertThat(board.newEvents().single()).isEqualTo(BoardCreatedEvent("boardId"))
    }
}