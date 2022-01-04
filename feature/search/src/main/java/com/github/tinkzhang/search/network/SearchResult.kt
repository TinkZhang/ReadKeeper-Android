package com.github.tinkzhang.search.network

sealed class SearchResult<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : SearchResult<T>(data)
    class Error<T>(message: String) : SearchResult<T>(message = message)
    class Loading
}
