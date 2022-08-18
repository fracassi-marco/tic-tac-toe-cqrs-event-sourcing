package domain.board

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.RuntimeException

class BoardTest {
    @Test
    fun `generate board created event`() {
        val board = Board("boardId", "player1Id", "player2Id")

        assertThat(board.newEvents().single()).isEqualTo(BoardCreatedEvent("boardId", "player1Id", "player2Id"))
    }

    @Test
    fun `generate sign added event`() {
        val board = Board("boardId", "player1Id", "player2Id")

        board.markCell(0, 0, "player1Id")

        assertThat(board.newEvents().last()).isEqualTo(CellMarkedEvent("boardId", 0, 0, "player1Id"))
    }

    @Test
    fun `can not mark already full cell`() {
        val board = Board("boardId", "player1Id", "player2Id")
        board.markCell(0, 0, "player1Id")

        assertThrows<RuntimeException> { board.markCell(0, 0, "player2Id") }
    }
}