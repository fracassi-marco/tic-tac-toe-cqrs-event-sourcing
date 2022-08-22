package domain.match

import domain.DomainEvent
import java.time.LocalDateTime
import java.util.*

data class MatchCreatedEvent(
    private val matchId: UUID,
    val player1Id: String,
    val player2Id: String,
    val timestamp: LocalDateTime = LocalDateTime.now()
) : DomainEvent {
    override fun aggregateId() = matchId
    override fun timestamp() = timestamp
}
