package domain.match

import java.util.*

data class MarkCellCommand(val matchId: UUID, val row: Int, val column: Int, val playerId: String)