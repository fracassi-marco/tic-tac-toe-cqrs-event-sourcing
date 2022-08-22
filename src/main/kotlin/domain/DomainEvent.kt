package domain

import java.time.LocalDateTime
import java.util.*

interface DomainEvent {
    fun aggregateId(): UUID
    fun timestamp(): LocalDateTime
}