import omok.model.external.OmokRule
import omok.model.game.Board
import omok.model.state.*
import omok.model.stone.Coordinate
import omok.model.stone.GoStone
import omok.model.stone.GoStoneColor

class StateJudgement(
    private val board: Board,
    private val goStone: GoStone
) {
    private val omokRule: OmokRule = OmokRule(board.convert(), convertColor(goStone), reverseColor(goStone.color))

    fun countOpenThrees(coordinate: Coordinate): State {
        val pair = coordinate.toPair()
        return if (omokRule.countOpenThrees(pair.first, pair.second) >= 2) {
            ForbiddenThree(board)
        } else Stay(board)
    }

    fun countOpenFours(coordinate: Coordinate): State {
        val pair = coordinate.toPair()
        return if (omokRule.countOpenFours(pair.first, pair.second) >= 2) {
            ForbiddenFour(board)
        } else Stay(board)
    }

    fun checkBlackWin(coordinate: Coordinate): State {
        val pair = coordinate.toPair()
        return if (omokRule.validateBlackWin(pair.first, pair.second)) Win(board) else Stay(board)
    }

    fun checkWhiteWin(coordinate: Coordinate): State {
        val pair = coordinate.toPair()
        return if (omokRule.validateWhiteWin(pair.first, pair.second)) Win(board) else Stay(board)
    }

    private fun Coordinate.toPair(): Pair<Int, Int> {
        return this.x to this.y
    }

    companion object {
        private fun Board.convert(): List<List<Int>> {
            return board.map { it.map(::convertColor) }
        }

        private fun convertColor(stone: GoStone?): Int {
            return when {
                stone == null -> 0
                stone.color == GoStoneColor.BLACK -> 1
                stone.color == GoStoneColor.WHITE -> 2
                else -> throw IllegalArgumentException()
            }
        }

        private fun reverseColor(stoneColor: GoStoneColor): Int {
            return when (stoneColor) {
                GoStoneColor.BLACK -> 2
                GoStoneColor.WHITE -> 1
            }
        }
    }
}