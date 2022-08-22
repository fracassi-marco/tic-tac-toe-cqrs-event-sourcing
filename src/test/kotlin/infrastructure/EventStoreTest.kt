package infrastructure

import domain.DomainEvent
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.UUID

class EventStoreTest {

    private val eventStore = EventStore()

    @Test
    fun `no events`() {
        val aggregateId = UUID.randomUUID()

        val events = eventStore.load(aggregateId)
        assertThat(events).isEmpty()
    }

    @Test
    fun `ordered load`() {
        val aggregateId = UUID.randomUUID()

        val baseTimestamp = LocalDateTime.now()
        eventStore.store(MyEvent(aggregateId, baseTimestamp.plusNanos(3)))
        eventStore.store(MyEvent(aggregateId, baseTimestamp.plusNanos(1)))
        eventStore.store(MyEvent(aggregateId, baseTimestamp.plusNanos(2)))

        val events = eventStore.load(aggregateId)
        assertThat(events).isEqualTo(listOf(
            MyEvent(aggregateId, baseTimestamp.plusNanos(1)),
            MyEvent(aggregateId, baseTimestamp.plusNanos(2)),
            MyEvent(aggregateId, baseTimestamp.plusNanos(3))
        ))
    }

    data class MyEvent(private val aggregateId: UUID, private val timestamp: LocalDateTime): DomainEvent {
        override fun aggregateId()= aggregateId
        override fun timestamp() = timestamp
    }
}