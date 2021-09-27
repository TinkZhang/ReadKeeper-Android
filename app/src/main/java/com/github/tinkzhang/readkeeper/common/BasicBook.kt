package com.github.tinkzhang.readkeeper.common

import com.google.firebase.Timestamp

abstract class BasicBook {
    abstract var title: String
    abstract var imageUrl: String
    abstract var author: String
    abstract var pages: Int
    abstract var addedTime: Timestamp
    abstract var rating: Double
    abstract var originalPublicationYear: Int
}
