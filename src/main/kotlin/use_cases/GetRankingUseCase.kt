package use_cases

import domain.DomainEvent
import domain.RankingRepository
import domain.Subscriber
import domain.match.CellMarkedEvent
import domain.match.MatchWonEvent

class GetRankingUseCase(private val rankingRepository: RankingRepository) : Subscriber<DomainEvent> {

    override fun handle(event: DomainEvent) {
        when (event) {
            is CellMarkedEvent -> rankingRepository.incrementMarks(event.playerId)
            is MatchWonEvent -> rankingRepository.incrementWon(event.playerId)
        }
    }

    fun process() = rankingRepository.getOrdered()
}