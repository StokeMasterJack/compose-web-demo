package ss.bj.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class CardTest {

    @Test
    fun test1() {
        val c1 = Card(0)
        val c2 = Card(51)
        val c3 = Card(14)

        assertEquals(0, c1.index)
        assertEquals(1, c1.value)
        assertEquals(1, c1.suit)
        assertEquals("Spades", c1.suitName)
        assertEquals("Ace", c1.valueName)
        assertEquals(1, c1.points)
        assertEquals("Ace of Spades", c1.name)   //NEW

        assertEquals(51, c2.index)
        assertEquals(13, c2.value)
        assertEquals(4, c2.suit)
        assertEquals("Diamonds", c2.suitName)
        assertEquals("King", c2.valueName)
        assertEquals(10, c2.points)
        assertEquals("King of Diamonds", c2.name) //NEW

        assertEquals(14, c3.index)
        assertEquals(2, c3.value)
        assertEquals(2, c3.suit)
        assertEquals("Hearts", c3.suitName)
        assertEquals("2", c3.valueName)
        assertEquals(2, c3.points)
        assertEquals("2 of Hearts", c3.name) //NEW
    }

    @Test
    fun cardInit() {
        try {
            Card(300)
            fail()
        } catch (e: Exception) {
            //good
            assertEquals(e.message, "Bad index: 300")
        }

    }

}