package domain

interface EventBus {
    fun publish(event: DomainEvent)
    fun register(subscriber: Subscriber<DomainEvent>)
}