package com.chrislicoder.foodrecipes_dbcache.util.network

class Resource<T> private constructor(val status: Status, val data: T, val message: String?) {
    sealed class Status {
        object SUCCESS : Status()
        object ERROR : Status()
        object LOADING : Status()
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String?, data: T): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}