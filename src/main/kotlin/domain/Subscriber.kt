package domain

interface Subscriber<DomainEvent> {
    fun handle(event: DomainEvent)
}