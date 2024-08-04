package org.kamilimu.books.viewbooks.data.mapper

import org.kamilimu.books.viewbooks.domain.model.ApiError
import org.kamilimu.books.viewbooks.domain.model.NetworkError
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toNetworkError(): NetworkError {
    val error = when (this) {
        is IOException -> ApiError.NETWORK_ERROR
        is HttpException -> ApiError.UNKNOWN_RESPONSE
        is NoSuchMethodException -> ApiError.CLIENT_ERROR
        else -> ApiError.UNKNOWN_ERROR
    }

    return NetworkError(
        error = error,
        throwable = this
    )
}