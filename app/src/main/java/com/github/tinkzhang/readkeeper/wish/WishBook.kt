package com.github.tinkzhang.readkeeper.wish

import com.github.tinkzhang.readkeeper.common.BasicBook
import com.google.firebase.Timestamp

data class WishBook(
    override var title: String = "",
    override var imageUrl: String = "",
    override var author: String ="",
    override var pages: Int = 0,
    override var addedTime: Timestamp = Timestamp.now(),
    override var rating: Double = 0.0,
    override var originalPublicationYear: Int = 1900,
    val labels: MutableList<String> = mutableListOf(),
) : BasicBook()
