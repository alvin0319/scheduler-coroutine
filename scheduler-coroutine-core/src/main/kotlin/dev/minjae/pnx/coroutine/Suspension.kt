package dev.minjae.pnx.coroutine

import kotlinx.coroutines.yield

/**
 * Utility class to process Coroutine's delayed job flexible according to the elapsed time.
 */
class Suspension {
    private val currentMillis: Long
        get() = System.currentTimeMillis() / 1_000_000L

    private var delayMillis = currentMillis

    /**
     * Adds a delayed time to the current time.
     *
     * If the delayed time is less than the current time, the coroutine will yield.
     * If the delayed time is greater than the current time, the coroutine will delay.
     */
    suspend fun delay(timeMillis: Long) {
        val delayMillis = (delayMillis + timeMillis).also { delayMillis = it }
        val actualDelay = delayMillis - currentMillis

        if (actualDelay > 0) {
            kotlinx.coroutines.delay(actualDelay)
        } else {
            yield()
        }
    }

    /**
     * Resets the delayed time to the current time.
     */
    fun reset() {
        delayMillis = currentMillis
    }
}
