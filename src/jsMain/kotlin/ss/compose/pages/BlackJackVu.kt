package ss.compose.pages

import ss.compose.components.Col
import ss.compose.components.HGap
import ss.compose.components.Row
import ss.compose.components.VGap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.flexGrow
import org.jetbrains.compose.web.css.fontWeight
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.css.maxWidth
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text
import ss.bj.model.Game
import ss.bj.model.Hand

@Composable
fun BlackjackVu() {

    var game: Game by mutableStateOf(Game().deal())

    Col({
        maxWidth(600.px)
    }) {
        VGap()
        Row({ justifyContent(JustifyContent.Center) }) {
            Button({
                style {
                    width(5.cssRem)
                }
                if (game.isGameActive) disabled()
                onClick { game = game.deal() }
            }) { Text("Deal") }
            HGap()
            Button({
                style {
                    width(5.cssRem)
                }
                if (game.isGameOver) disabled()
                onClick { game = game.hit() }
            }) { Text("Hit") }
            HGap()
            Button({
                style {
                    width(5.cssRem)
                }
                if (game.isGameOver) disabled()
                onClick { game = game.stay() }
            }) { Text("Stay") }
        }
        VGap()
        Row {
            HGap()
            HandVu(game.playerHand)
            HGap()
            HandVu(game.dealerHand)
            HGap()
        }
        VGap()
        Row({
            justifyContent(JustifyContent.Center)
            fontWeight("bold")
        }) {
            Text(game.msg)
        }
        VGap()
        Row {

        }
    }

}


@Composable
fun HandVu(hand: Hand) {
    Col({
        padding(10.px)
        backgroundColor(Color.lightgray)
        height(20.cssRem)
        width(10.cssRem)
        flexGrow(1)
    }) {
        Row({
            fontWeight("bold")
        }) {
            Text(hand.name)
        }
        Col {
            hand.cards.forEach {
                Row {
                    Text(it.name)
                }
            }
        }
        Row({
            fontWeight("bold")
        }) { Text(hand.msg) }
    }
}
