package infrastructure

import domain.DomainEvent
import domain.Subscriber
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.UUID.randomUUID

class SyncEventBusTest {
    private val eventBus = SyncEventBus()

    @Test
    fun `no event`() {
        val subscriberA = DummySubscriber()
        eventBus.register(subscriberA)

        assertThat(subscriberA.handledEventCount).isEqualTo(0)
    }

    @Test
    fun `post events to subscribers`() {
        val subscriber1 = DummySubscriber()
        val subscriber2 = DummySubscriber()
        eventBus.register(subscriber1)
        eventBus.register(subscriber2)
        eventBus.publish(DomainEvent(randomUUID(), 0))
        eventBus.publish(DomainEvent(randomUUID(), 0))

        assertThat(subscriber1.handledEventCount).isEqualTo(2)
        assertThat(subscriber2.handledEventCount).isEqualTo(2)
    }

    class DummySubscriber: Subscriber<DomainEvent> {
        var handledEventCount = 0

        override fun handle(event: DomainEvent) {
            handledEventCount++
        }
    }
}