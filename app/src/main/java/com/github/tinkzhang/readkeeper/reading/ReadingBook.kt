package com.github.tinkzhang.readkeeper.reading

import com.github.tinkzhang.readkeeper.common.BasicBook
import com.google.firebase.Timestamp

data class ReadingBook(
    override var title: String = "",
    override var imageUrl: String = "",
    override var author: String = "",
    override var pages: Int = 0,
    override var addedTime: Timestamp = Timestamp.now(),
    override var rating: Double = 0.0,
    override var originalPublicationYear: Int = 1900,
    val platform: ReadingPlatform = ReadingPlatform.PAPER,
    val labels: MutableList<String> = mutableListOf(),
    val readingRecords: MutableList<ReadingRecord> = mutableListOf(),
    val readingNotes: MutableList<ReadingNote> = mutableListOf(),
) : BasicBook()

data class ReadingRecord(
    val startPage: Double = 0.0,
    val endPage: Double = 0.0,
    val timestamp: Timestamp = Timestamp.now()
)

data class ReadingNote(
    val note: String = "",
    val timestamp: Timestamp = Timestamp.now()
)

enum class ReadingPlatform(val label: String) {
    PAPER("Paper"),
    PDF("PDF" ),
    APPLE_BOOKS("Apple Book"),
    WECHAT("WeChat Read"),
    KINDLE("Kindle");
}

class ReadingBookFactory {
    companion object {
        fun buildSample(): ReadingBook {
            val records = mutableListOf(ReadingRecord(startPage = 12.0, endPage = 112.0))
            val notes = mutableListOf(ReadingNote("Hello world"))
            return ReadingBook(
                title = "Book Title",
                imageUrl = "http://books.google.com/books/content?id=gK98gXR8onwC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                author = "Tink",
                pages = 1688,
                rating = 5.0,
                addedTime = Timestamp.now(),
                originalPublicationYear = 1987,
                labels = mutableListOf("Java", "Jobs"),
                readingRecords = records,
                readingNotes = notes
            )
        }
    }
}
