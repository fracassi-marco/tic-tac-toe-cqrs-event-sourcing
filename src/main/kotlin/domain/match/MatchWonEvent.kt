package domain.match

import domain.DomainEvent
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.*

data class MatchWonEvent(
    private val matchId: UUID,
    val playerId: String,
    val timestamp: LocalDateTime = now()
) : DomainEvent {
    override fun aggregateId() = matchId
    override fun timestamp() = timestamp
}
