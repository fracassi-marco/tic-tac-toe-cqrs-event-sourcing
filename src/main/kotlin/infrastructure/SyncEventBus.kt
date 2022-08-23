package infrastructure

import domain.DomainEvent
import domain.EventBus
import domain.Subscriber

class SyncEventBus: EventBus {
    private val subscribers = mutableListOf<Subscriber<DomainEvent>>()

    override fun publish(event: DomainEvent) {
        subscribers.forEach {
            it.handle(event)
        }
    }

    override fun register(subscriber: Subscriber<DomainEvent>) {
        subscribers.add(subscriber)
    }
}

