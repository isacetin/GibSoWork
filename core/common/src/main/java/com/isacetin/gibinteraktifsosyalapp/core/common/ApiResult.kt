package com.isacetin.gibinteraktifsosyalapp.core.common

/**
 * Generic wrapper for a remote call outcome, carrying the HTTP status code on
 * error so callers can react to PostgREST-specific codes (400/404/409...).
 */
sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>
    data class Error(val message: String, val code: Int? = null) : ApiResult<Nothing>
}

/** Maps a network-layer [ApiResult] to a [Result] consumable by the domain layer. */
fun <T> ApiResult<T>.toResult(): Result<T> = when (this) {
    is ApiResult.Success -> Result.success(data)
    is ApiResult.Error -> Result.failure(ApiException(message, code))
}

class ApiException(message: String, val code: Int? = null) : Exception(message)
