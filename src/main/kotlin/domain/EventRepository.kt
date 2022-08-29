package domain

import java.util.*

interface EventRepository {
    fun store(event: DomainEvent, aggregateVersion: Long)
    fun load(aggregateId: UUID): List<DomainEvent>
}