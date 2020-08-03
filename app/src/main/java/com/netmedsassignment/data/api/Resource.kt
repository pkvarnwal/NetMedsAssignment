package com.netmedsassignment.data.api

//To maintain the status of Success, Error and Loading of Api response
data class Resource<out T>(val status: Status, val data: T?, val message: Any?) {

    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(message: Any?, data: T?): Resource<T> =
            Resource(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T?): Resource<T> =
            Resource(status = Status.LOADING, data = data, message = null)
    }
}