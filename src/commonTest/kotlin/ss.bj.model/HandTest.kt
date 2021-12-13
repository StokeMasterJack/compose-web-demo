package ss.bj.model

import kotlin.test.Test
import kotlin.test.assertEquals

class HandTest {

    @Test
    fun test() {
        Hand(HandType.Player).apply {
            assertEquals(0, size)
            assertEquals(0, points)
            check(!isDone)
        }.plus(Card(0)).apply {
            assertEquals(1, size)
            assertEquals(1, points)
            check(!isDone)
        }.plus(Card(51)).apply {
            assertEquals(2, size)
            assertEquals(11, points)
            check(!isDone)
        }.plus(listOf(Card(2), Card(3))).apply {
            assertEquals(4, size)
            assertEquals(18, points)
            check(!isDone)
        }.plus(Card(5)).apply {
            assertEquals(5, size)
            assertEquals(24, points)
            check(isDone)
        }
    }

}