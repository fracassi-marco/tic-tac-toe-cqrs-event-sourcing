package domain

interface EventRepository {
    fun store(event: DomainEvent)
}