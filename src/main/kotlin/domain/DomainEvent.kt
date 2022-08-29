package domain

import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.*

open class DomainEvent(val aggregateId: UUID, val aggregateVersion: Long, val timestamp: LocalDateTime = now())