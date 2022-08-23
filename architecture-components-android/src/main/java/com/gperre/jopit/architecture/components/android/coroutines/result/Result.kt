package com.gperre.jopit.architecture.components.android.coroutines.result

import kotlinx.coroutines.CancellationException

sealed class Result<T> {
    data class Success<T>(val value: T) : Result<T>()
    data class Failure<T>(val error: Throwable) : Result<T>()
}

/**
 * Performs the given [action] on the encapsulated [Throwable] exception if this instance
 * represents [failure][Result.Failure].
 * Returns the original `Result` unchanged.
 */
inline fun <T> Result<T>.onFailure(action: (exception: Throwable) -> Unit): Result<T> {
    if (this is Result.Failure) action(error)
    return this
}

/**
 * Performs the given [action] on the encapsulated value if this instance
 * represents [success][Result.Success].
 * Returns the original `Result` unchanged.
 */
inline fun <T> Result<T>.onSuccess(action: (value: T) -> Unit): Result<T> {
    if (this is Result.Success) action(value)
    return this
}

/**
 * Like [runCatching], but with proper coroutines cancellation handling. Also only catches [Exception] instead of [Throwable].
 *
 * Cancellation exceptions need to be rethrown.
 */
inline fun <R> resultOf(block: () -> R): Result<R> {
    return try {
        Result.Success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.Failure(e)
    }
}

/**
 * Like [runCatching], but with proper coroutines cancellation handling. Also only catches [Exception] instead of [Throwable].
 *
 * Cancellation exceptions need to be rethrown.
 */
inline fun <T, R> T.resultOf(block: T.() -> R): Result<R> {
    return try {
        Result.Success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.Failure(e)
    }
}
