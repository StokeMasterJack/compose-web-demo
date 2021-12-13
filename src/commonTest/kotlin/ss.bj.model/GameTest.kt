package ss.bj.model

import kotlin.test.Test
import kotlin.test.assertEquals

class GameTest {

    @Test
    fun test() {
        println("AAA")
        Game(shuffle = false).apply {
            assertEquals(52, deck.size)
            assertEquals(0, playerHand.size)
            assertEquals(0, playerHand.points)
            assertEquals(0, dealerHand.size)
            assertEquals(0, dealerHand.points)
//            assertTrue { state is NotStarted }
        }.deal().apply {
            //ph: ace, 2
            //dh: 3, 4
            assertEquals(48, deck.size)
            assertEquals(2, playerHand.size)
            assertEquals(3, playerHand.points)
            assertEquals(2, dealerHand.size)
            assertEquals(7, dealerHand.points)
//            assertTrue { state is Active }
        }.hit().apply {
            //ph: ace, 2, 5
            //dh: 3, 4
            assertEquals(47, deck.size)
            assertEquals(3, playerHand.size)
            assertEquals(8, playerHand.points)
            assertEquals(2, dealerHand.size)
            assertEquals(7, dealerHand.points)
//            check(state is Active)
        }.hit().apply {
            //ph: ace, 2, 5, 6
            //dh: 3, 4
            assertEquals(46, deck.size)
            assertEquals(4, playerHand.size)
            assertEquals(2, dealerHand.size)
//            check(state is Active)
        }.hit().apply {
            //ph: ace, 2, 5, 6, 7
            //dh: 3, 4
            assertEquals(45, deck.size)
            assertEquals(5, playerHand.size)
            assertEquals(2, dealerHand.size)
//            check(state is Active)
        }.deal().apply {
            //ph:  8 9
            //dh:  10 J
            assertEquals(41, deck.size)
            assertEquals(2, playerHand.size)
            assertEquals(17, playerHand.points)
            assertEquals(2, dealerHand.size)
            assertEquals(20, dealerHand.points)
//            check(state is Active)
        }.stay().apply {
            //sets ph.stay to true and auto-hits dealer as long as points < 17
            //ph:  8 9
            //dh:  10 J
            assertEquals(41, deck.size)
            assertEquals(2, playerHand.size)
            assertEquals(17, playerHand.points)
            check(playerHand.isStay)
            assertEquals(2, dealerHand.size)
            assertEquals(20, dealerHand.points)
//            check(state is Over)
        }

        println("done")
    }

    @Test
    fun testManyDeals() {
        var g = Game()
        repeat(100) {
            g = g.deal()
            check(g.isDealClean)
            check(g.state is Active)
            g = g.hit()
            check(!g.isDealClean)
            g = g.stay()
            check(g.state is Over)
        }
    }
}