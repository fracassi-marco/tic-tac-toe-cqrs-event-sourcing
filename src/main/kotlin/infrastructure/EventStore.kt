package infrastructure

import domain.DomainEvent
import domain.EventRepository
import java.util.*

class EventStore: EventRepository {

    private val eventsByAggregate = mutableMapOf<UUID, List<DomainEvent>>()

    override fun store(event: DomainEvent) {
        eventsByAggregate.merge(event.aggregateId(), listOf(event)) { oldValue, value ->
            oldValue + value
        }
    }

    override fun load(aggregateId: UUID): List<DomainEvent> {
        return eventsByAggregate.getOrDefault(aggregateId, emptyList()).sortedBy { it.timestamp() }
    }
}
