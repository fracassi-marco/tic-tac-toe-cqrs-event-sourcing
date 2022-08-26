package infrastructure

import domain.RankingRepository
import domain.ranking.RankingPosition

class InMemoryRankingRepository: RankingRepository {

    private val ranking = mutableMapOf<String, RankingPosition>()

    override fun incrementMarks(playerId: String) {
        merge(playerId, RankingPosition(playerId, 0, 1))
    }

    override fun incrementWons(playerId: String) {
        merge(playerId, RankingPosition(playerId, 1, 0))
    }

    override fun getOrdered(): List<RankingPosition> {
        return ranking.values.sortedWith(compareByDescending<RankingPosition> { it.wins }.thenBy { it.marks })
    }

    private fun merge(playerId: String, rankingPosition: RankingPosition) {
        ranking.merge(playerId, rankingPosition) { oldValue, value ->
            RankingPosition(oldValue.playerId, oldValue.wins + value.wins, oldValue.marks + value.marks)
        }
    }
}