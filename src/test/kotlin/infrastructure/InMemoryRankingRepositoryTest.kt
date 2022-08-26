package infrastructure

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class InMemoryRankingRepositoryTest {

    private val repository = InMemoryRankingRepository()

    @Test
    fun `increment wons`() {
        repository.incrementWons("player1")
        repository.incrementWons("player1")

        assertThat(repository.getOrdered().single().wins).isEqualTo(2)
    }

    @Test
    fun `increment marks`() {
        repository.incrementMarks("player1")
        repository.incrementMarks("player1")

        assertThat(repository.getOrdered().single().marks).isEqualTo(2)
    }

    @Test
    fun `order by wins`() {
        repository.incrementWons("player2")
        repository.incrementWons("player1")
        repository.incrementWons("player1")

        assertThat(repository.getOrdered().first().playerId).isEqualTo("player1")
        assertThat(repository.getOrdered().last().playerId).isEqualTo("player2")
    }

    @Test
    fun `order by fewest marks when have same number of wins`() {
        repository.incrementWons("player2")
        repository.incrementWons("player1")
        repository.incrementMarks("player2")

        assertThat(repository.getOrdered().first().playerId).isEqualTo("player1")
        assertThat(repository.getOrdered().last().playerId).isEqualTo("player2")
    }
}