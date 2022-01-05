package com.example.haritagedemo.API

abstract class ResponseListener<in T>:OnListener<T> {
    override fun onRequestSuccess(response: T) {
        TODO("Not yet implemented")
    }

    override fun onRequestSuccess(isSuccess: Boolean, response: T, message: String) {
        TODO("Not yet implemented")
    }

    override fun onRequestFailed(t: Throwable) {
        TODO("Not yet implemented")
    }

    override fun onRequestFailed(message: String) {
        TODO("Not yet implemented")
    }
}

interface OnListener<in T> {
    fun onRequestSuccess(response: T)
    fun onRequestSuccess(
        isSuccess: Boolean,
        response: T,
        message: String
    )  // use this method in handle pagination response

    fun onRequestFailed(t: Throwable)
    fun onRequestFailed(message: String)
}
