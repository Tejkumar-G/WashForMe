package com.example.washforme.db

interface ApiListener<T> {
    fun onLoading()
    fun onSuccess(response: T?)
    fun onFailure(message: String)
}