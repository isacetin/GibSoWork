package com.isacetin.gibinteraktifsosyalapp.core.network

import com.isacetin.gibinteraktifsosyalapp.core.common.ApiResult
import retrofit2.HttpException
import java.io.IOException

/** Wraps a Retrofit call, turning HTTP/IO failures into [ApiResult.Error]. */
suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResult<T> = try {
    ApiResult.Success(apiCall())
} catch (e: HttpException) {
    ApiResult.Error(e.message(), e.code())
} catch (e: IOException) {
    ApiResult.Error(e.message ?: "Ağ hatası, bağlantınızı kontrol edin")
}
