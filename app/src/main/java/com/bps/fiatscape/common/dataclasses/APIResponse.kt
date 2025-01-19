package com.bps.fiatscape.common.dataclasses

sealed class APIResponse<out T> {
    data class Success<T>(val data: T): APIResponse<T>()
    data class Error(val statusCode: Int, val message: String): APIResponse<Nothing>()
    object Loading: APIResponse<Nothing>()
}
