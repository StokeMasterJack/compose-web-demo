package ss.kutil

import ss.bj.model.Card

/**
 * Calls the specified function [block] with `this` value as its receiver and returns its result.
 *
 * For detailed usage information see the documentation for [scope functions](https://kotlinlang.org/docs/reference/scope-functions.html#run).
 */
fun <T> T.runIf(test: Boolean, block: T.() -> T): T = run { if (test) this.block() else this }
fun <T> T.runIf(predicate: () -> Boolean, block: T.() -> T): T = run { if (predicate()) this.block() else this }

fun <T> List<T>.shuffledIf(predicate: () -> Boolean): List<T> = if (predicate()) this.shuffled() else this
fun <T> List<T>.shuffledIf(test: Boolean): List<T> = if (test) this.shuffled() else this

fun List<Card>.dump() {
    forEach {
        println(it)
    }
}