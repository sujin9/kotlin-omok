package omok.model.game

import OmokRuleConverter
import omok.model.state.DoubleFour
import omok.model.state.DoubleThree
import omok.model.state.Stay
import omok.model.state.Win
import omok.model.stone.Coordinate
import omok.model.stone.GoStone
import omok.model.stone.GoStoneColor.BLACK
import omok.model.stone.GoStoneColor.WHITE
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokRuleConverterTest {
    @Test
    fun `5개의 같은 색의 돌이 가로로 연이어 놓이면 승리이다`() {
        val board = Board().apply {
            addStone(BLACK, Coordinate("H8"))
            addStone(BLACK, Coordinate("H9"))
            addStone(BLACK, Coordinate("H11"))
            addStone(BLACK, Coordinate("H12"))
        }
        val goStone = GoStone(BLACK, Coordinate("H10"))
        val rule = OmokRuleConverter(board)

        assertThat(
            rule.checkBlackWin(goStone.coordinate)
        ).isInstanceOf(Win::class.java)
    }

    @Test
    fun `5개의 같은 색의 돌이 세로로 연이어 놓이면 승리이다`() {
        val board = Board().apply {
            addStone(BLACK, Coordinate("F10"))
            addStone(BLACK, Coordinate("G10"))
            addStone(BLACK, Coordinate("I10"))
            addStone(BLACK, Coordinate("J10"))
        }
        val goStone = GoStone(BLACK, Coordinate("H10"))
        val rule = OmokRuleConverter(board)

        assertThat(
            rule.checkBlackWin(goStone.coordinate)
        ).isInstanceOf(Win::class.java)
    }

    @Test
    fun `5개의 같은 색의 돌이 우상향 대각선으로 연이어 놓이면 승리이다`() {
        val board = Board().apply {
            addStone(WHITE, Coordinate("F8"))
            addStone(WHITE, Coordinate("G9"))
            addStone(WHITE, Coordinate("I11"))
            addStone(WHITE, Coordinate("J12"))
        }
        val goStone = GoStone(WHITE, Coordinate("H10"))
        val rule = OmokRuleConverter(board)

        assertThat(
            rule.checkWhiteWin(goStone.coordinate)
        ).isInstanceOf(Win::class.java)
    }

    @Test
    fun `5개의 같은 색의 돌이 우하향 대각선으로 연이어 놓이면 승리이다`() {
        val board = Board().apply {
            addStone(BLACK, Coordinate("F12"))
            addStone(BLACK, Coordinate("G11"))
            addStone(BLACK, Coordinate("I9"))
            addStone(BLACK, Coordinate("J8"))
        }
        val goStone = GoStone(BLACK, Coordinate("H10"))
        val rule = OmokRuleConverter(board)

        assertThat(
            rule.checkBlackWin(goStone.coordinate)
        ).isInstanceOf(Win::class.java)
    }

    @Test
    fun `연달아 놓여져 있는 5개의 돌들의 색이 서로 다르면 승리가 아니다`() {
        val board = Board().apply {
            addStone(BLACK, Coordinate("H8"))
            addStone(BLACK, Coordinate("H9"))
            addStone(WHITE, Coordinate("H11"))
            addStone(BLACK, Coordinate("H12"))
        }
        val goStone = GoStone(BLACK, Coordinate("H10"))
        val rule = OmokRuleConverter(board)

        assertThat(
            rule.checkBlackWin(goStone.coordinate)
        ).isInstanceOf(Stay::class.java)
    }

    @Test
    fun `놓여져 있는 돌들이 5개보다 적으면 승리가 아니다`() {
        val board = Board().apply {
            addStone(BLACK, Coordinate("H8"))
            addStone(BLACK, Coordinate("H9"))
            addStone(WHITE, Coordinate("H11"))
        }
        val goStone = GoStone(BLACK, Coordinate("H10"))
        val rule = OmokRuleConverter(board)

        assertThat(
            rule.checkBlackWin(goStone.coordinate)
        ).isInstanceOf(Stay::class.java)
    }

    @Test
    fun `6개의 같은 색의 돌이 가로로 연이어 놓이면 승리이다`() {
        val board = Board().apply {
            addStone(BLACK, Coordinate("H8"))
            addStone(BLACK, Coordinate("H9"))
            addStone(BLACK, Coordinate("H11"))
            addStone(BLACK, Coordinate("H12"))
            addStone(BLACK, Coordinate("H13"))
        }
        val goStone = GoStone(BLACK, Coordinate("H10"))
        val rule = OmokRuleConverter(board)

        assertThat(
            rule.checkBlackWin(goStone.coordinate)
        ).isInstanceOf(Win::class.java)
    }

    @Test
    fun `흑돌이 열린 33이다`() {
        val board = Board().apply {
            addStone(BLACK, Coordinate("C12"))
            addStone(BLACK, Coordinate("E12"))
            addStone(BLACK, Coordinate("D13"))
            addStone(BLACK, Coordinate("D14"))
        }
        val rule = OmokRuleConverter(board)

        assertThat(
            rule.countOpenThrees(Coordinate("D12"))
        ).isInstanceOf(DoubleThree::class.java)
    }

    @Test
    fun `흑돌이 44이다`() {
        val board = Board().apply {
            addStone(BLACK, Coordinate("E5"))
            addStone(BLACK, Coordinate("F5"))
            addStone(BLACK, Coordinate("G5"))
            addStone(BLACK, Coordinate("H6"))
            addStone(BLACK, Coordinate("H7"))
            addStone(BLACK, Coordinate("H8"))
            addStone(WHITE, Coordinate("D5"))
            addStone(WHITE, Coordinate("H9"))
        }
        val goStone = GoStone(BLACK, Coordinate("H5"))
        val rule = OmokRuleConverter(board)

        assertThat(
            rule.countOpenFours(goStone.coordinate)
        ).isInstanceOf(DoubleFour::class.java)
    }
}