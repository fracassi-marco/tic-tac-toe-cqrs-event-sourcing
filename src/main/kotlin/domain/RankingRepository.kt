package domain

import domain.ranking.RankingPosition

interface RankingRepository {
    fun incrementMarks(playerId: String)
    fun incrementWon(playerId: String)
    fun getOrdered(): List<RankingPosition>
}