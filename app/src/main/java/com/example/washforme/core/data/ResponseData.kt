package com.example.washforme.core.data

data class ResponseData<out T>(val status: Status, val payload: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): ResponseData<T> {
            return ResponseData(Status.SUCCESS, data, null)
        }

        fun <T> failure(msg: String, data: T?): ResponseData<T> {
            return ResponseData(Status.FAILURE, data, msg)
        }

        fun <T> loading(data: T?): ResponseData<T> {
            return ResponseData(Status.LOADING, data, null)
        }
    }

    fun isSuccess() =
        status == Status.SUCCESS

}

enum class Status {
    SUCCESS,
    FAILURE,
    LOADING
}