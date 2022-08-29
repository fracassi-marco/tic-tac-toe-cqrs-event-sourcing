package infrastructure

import domain.DomainEvent
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.*

class EventStoreTest {

    private val eventStore = EventStore()

    @Test
    fun `no events`() {
        val aggregateId = UUID.randomUUID()

        val events = eventStore.load(aggregateId)
        assertThat(events).isEmpty()
    }

    @Test
    fun `optimistic locking`() {
        val aggregateId = UUID.randomUUID()
        eventStore.store(DomainEvent(aggregateId, 0), 0)
        assertThatThrownBy { eventStore.store(DomainEvent(aggregateId, 1), 5) }
    }
}