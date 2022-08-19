package domain.match

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GridTest {
    @Test
    fun `winner by row`() {
        val grid = Grid()
        grid.mark(0, 0, "X")
        grid.mark(0, 1, "X")
        grid.mark(0, 2, "X")

        assertThat(grid.getWinner()).isEqualTo("X")
    }

    @Test
    fun `winner by column`() {
        val grid = Grid()
        grid.mark(0, 0, "X")
        grid.mark(1, 0, "X")
        grid.mark(2, 0, "X")

        assertThat(grid.getWinner()).isEqualTo("X")
    }

    @Test
    fun `winner by right diagonal`() {
        val grid = Grid()
        grid.mark(0, 0, "X")
        grid.mark(1, 1, "X")
        grid.mark(2, 2, "X")

        assertThat(grid.getWinner()).isEqualTo("X")
    }

    @Test
    fun `winner by left diagonal`() {
        val grid = Grid()
        grid.mark(0, 2, "X")
        grid.mark(1, 1, "X")
        grid.mark(2, 0, "X")

        assertThat(grid.getWinner()).isEqualTo("X")
    }
}