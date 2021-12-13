package ss.bj.model

import kotlin.test.Test
import kotlin.test.assertEquals

class DeckTest {

    @Test
    fun test() {
        Deck(shuffle = false).apply {
            assertEquals(52, size)
            check(isVirgin)
            check(!isEmpty)
        }.takeCards().let {
            val (d, cards) = it
            check(!d.isVirgin)
            check(!d.isEmpty)
            assertEquals(51, d.size)
            assertEquals(1, cards.size)
            val (c0) = cards
            assertEquals(1, c0.value)
            assertEquals(1, c0.suit)
            d
        }.let {
            val (d, cards) = it.takeCards(2)
            assertEquals(2, cards.size)
            assertEquals(49, d.size)
            val (c1, c2) = cards
            assertEquals(2, c1.value)
            assertEquals(1, c1.suit)
            assertEquals(3, c2.value)
            assertEquals(1, c2.suit)
            d
        }.shuffled().apply {
            assertEquals(52, cards.size)
        }.shuffled().apply {
            assertEquals(52, cards.size)
        }


    }

}