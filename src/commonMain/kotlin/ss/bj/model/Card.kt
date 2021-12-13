package ss.bj.model

/**
 * @param index 0..51
 */
class Card(val index: Int) {

    init {
        require(index in 0..51) { "Bad index: $index" }
    }

    //1..13
    val value: Int get() = index % 13 + 1  // index % 13 + 1

    //1..4
    val suit: Int get() = index / 13 + 1

    val suitName: String
        get() = when (suit) {
            1 -> "Spades"
            2 -> "Hearts"
            3 -> "Clubs"
            4 -> "Diamonds"
            else -> throw IllegalStateException("Bad suit: $suit")
        }

    val valueName: String
        get() = when (value) {
            1 -> "Ace"
            in 2..10 -> value.toString()
            11 -> "Jack"
            12 -> "Queen"
            13 -> "King"
            else -> throw IllegalStateException("Bad value: $value")
        }

    val name: String get() = "$valueName of $suitName"

    /**
     * Ace is always 1 point
     */
    val points: Int
        get() = when (value) {
            in 1..9 -> value
            in 10..13 -> 10
            else -> throw IllegalStateException("Bad value: $value")
        }

    override fun toString(): String = name
}