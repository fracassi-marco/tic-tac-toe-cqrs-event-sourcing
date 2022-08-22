package domain

interface EventBus {
    fun post(event: DomainEvent)
}