package domain

import java.util.*

interface EventRepository {
    fun store(event: DomainEvent)
    fun load(aggregateId: UUID): List<DomainEvent>
}