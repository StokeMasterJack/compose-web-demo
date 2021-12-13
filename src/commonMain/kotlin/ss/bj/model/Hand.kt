package ss.bj.model

import ss.kutil.dump

enum class HandType {
    Dealer, Player
}

class Hand private constructor(val type: HandType, val cards: List<Card>, private val stay: Boolean) {

    constructor(type: HandType, cards: List<Card> = emptyList()) : this(
        type = type,
        cards = cards,
        stay = false
    )

    val isDealerStay: Boolean get() = points > 17

    val isStay: Boolean get() = stay

    operator fun plus(card: Card): Hand = cp(this.cards + card)
    operator fun plus(cards: List<Card>): Hand = cp(this.cards + cards)

    private fun cp(cards: List<Card>? = null, stay: Boolean? = null) = Hand(
        type = this.type,
        cards = cards ?: this.cards,
        stay = stay ?: this.stay
    )

    fun stay(): Hand = cp(stay = true)

    val points: Int get() = cards.sumOf { it.points }

    private val p: Int get() = points

    val size: Int get() = cards.size

    val isEmpty: Boolean get() = size == 0

    fun dump() {
        println("=================")
        println("$name Hand")
        println("=================")
        cards.dump()
        println("isStay = $isStay")
        println(msg)
        println("=================")
        println()
        println()
    }

    fun clear(cards: List<Card> = emptyList()) = cp(cards = cards, stay = false)

    val name: String get() = type.name

    val msg: String
        get() = when (p) {
            in 0..20 -> "$p points"
            21 -> "Blackjack!"
            in 22..200 -> "Bust!"
            else -> throw IllegalStateException()
        }

    val is21: Boolean get() = points == 21

    val isBust: Boolean get() = points > 21

    val isDone: Boolean get() = is21 || isBust || isStay

    val isDealClean: Boolean get() = size == 2 && !isStay

    companion object {
        fun ph() = Hand(type = HandType.Player)
        fun dh() = Hand(type = HandType.Dealer)
    }

}

