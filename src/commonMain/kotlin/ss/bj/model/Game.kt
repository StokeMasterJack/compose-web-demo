package ss.bj.model

enum class GState {
    NotStarted, Active, Over
}

sealed class GameState

object NotStarted : GameState()
object Active : GameState()
class Over(val winner: HandType) : GameState()

private fun hit(d: Deck, h: Hand, n: Int = 1): Pair<Deck, Hand> = d.takeCards(n).run {
    val (d2, cards) = this
    Pair(d2, h + cards)
}

private fun hitUntilDealerStay(d: Deck, h: Hand): Pair<Deck, Hand> = if (h.points > 17)
    Pair(d, h.stay())
else {
    val (d2, h2) = hit(d, h)
    hitUntilDealerStay(d2, h2)
}

class Game private constructor(val deck: Deck, val playerHand: Hand, val dealerHand: Hand) {

    constructor(shuffle: Boolean = true) : this(
        deck = Deck(shuffle),
        playerHand = Hand(HandType.Player),
        dealerHand = Hand(HandType.Player)
    )

    private fun cp(
        deck: Deck = this.deck,
        ph: Hand = this.playerHand,
        dh: Hand = this.dealerHand
    ) = Game(deck, ph, dh)


    fun deal(): Game {
        val (deck2, cards) = deck.shuffledIfRunningLow().takeCards(4)
        return cp(
            deck = deck2,
            ph = playerHand.clear(cards.take(2)),
            dh = dealerHand.clear(cards.takeLast(2))
        )
    }


    fun hit(): Game {
        val (deck2, ph2) = hit(deck, playerHand)
        return cp(deck = deck2, ph = ph2)
    }

    fun dump() {
        playerHand.dump()
        dealerHand.dump()
    }

    fun stay(): Game {
        check(!playerHand.isStay)
        check(!dealerHand.isStay)
        val ph2 = playerHand.stay()
        val (deck2, dh2) = hitUntilDealerStay(deck, dealerHand)
        check(ph2.isStay)
        check(dh2.isStay)
        return cp(deck = deck2, dh = dh2, ph = ph2)
    }

    val isVirgin: Boolean get() = playerHand.isEmpty && dealerHand.isEmpty && deck.isVirgin
    val isDealClean: Boolean get() = playerHand.isDealClean && dealerHand.isDealClean
    val isGameOver get() = playerHand.points>=21 || dealerHand.points>= 21 || dealerHand.isStay
    val isGameActive get() = !isVirgin && !isGameOver

    val stateEnum: GState = when {
        isVirgin -> GState.NotStarted
        isGameActive -> GState.Active
        isGameOver -> GState.Over
        else -> throw IllegalStateException()
    }

    val state: GameState = when {
        isVirgin -> NotStarted
        isGameActive -> Active
        isGameOver -> Over(
            when {
                playerHand.points == 21 -> HandType.Player
                playerHand.points > 21 -> HandType.Dealer
                dealerHand.points > 21 -> HandType.Player
                playerHand.points >= dealerHand.points -> HandType.Player
                else -> HandType.Dealer
            }
        )
        else -> throw IllegalStateException()
    }

    val msg: String
        get() {
//            check(state is Over && state.winner == HandType.Dealer)
            return when (state) {
                is NotStarted -> "Press Deal to start"
                is Active -> "Press Hit or Stay"
                is Over -> when (state.winner) {
                    HandType.Player -> "Game over. Player wins!"
                    HandType.Dealer -> "Game over. Dealer wins!"
                }
            }

        }
}
