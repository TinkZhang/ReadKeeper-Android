package com.github.tinkzhang.readkeeper.common

abstract class BasicBook {
    abstract var title: String
    abstract var imageUrl: String
    abstract var author: String
    abstract var pages: Int
    abstract var addedTime: Long
    abstract var rating: Double
    abstract var originalPublicationYear: Int
}
