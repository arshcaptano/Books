package org.kamilimu.books.viewbooks.domain.model

data class NetworkError(
    val error: ApiError,
    val throwable: Throwable
)

enum class ApiError(val message: String) {
    NETWORK_ERROR("Network error"),
    UNKNOWN_ERROR("Unknown error"),
    UNKNOWN_RESPONSE("Unknown response"),
    CLIENT_ERROR("Bad Request")
}