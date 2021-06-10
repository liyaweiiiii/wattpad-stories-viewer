package com.example.stories.model

data class Result<out T>(
    val status: Status,
    val data: T?,
    val error: Error?,
    val message: String?
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
        EVENT
    }

    companion object {
        fun <T> success(data: T?): Result<T> {
            return Result(Status.SUCCESS, data, null, null)
        }

        fun <T> error(error: Error?, message: String?): Result<T> {
            return Result(Status.ERROR, null, error, message)
        }

        fun <T> loading(): Result<T> {
            return Result(Status.LOADING, null, null, null)
        }

        fun <T> event(message: String): Result<T> {
            return Result(Status.EVENT, null, null, message)
        }
    }

    override fun toString(): String {
        return "Result(status=$status, data=$data, error=$error, message=$message)"
    }
}