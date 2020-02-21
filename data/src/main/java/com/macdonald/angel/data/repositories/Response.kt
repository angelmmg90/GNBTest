package com.macdonald.angel.data.repositories

sealed class Response<out T> {
    class Success<out T>(val data: T) : Response<T>()

    class Error(val exception: Throwable) : Response<Nothing>()

    object NetWorkError : Response<Nothing>()
}