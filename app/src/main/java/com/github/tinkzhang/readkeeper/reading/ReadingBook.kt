package com.github.tinkzhang.readkeeper.reading

import com.github.tinkzhang.readkeeper.R
import com.github.tinkzhang.readkeeper.common.BasicBook
import com.google.firebase.Timestamp
import kotlin.random.Random

data class ReadingBook(
    override var title: String = "",
    override var imageUrl: String = "",
    override var author: String = "",
    override var pages: Int = 0,
    override var addedTime: Timestamp = Timestamp.now(),
    override var rating: Double = 0.0,
    override var originalPublicationYear: Int = 1900,
    val platform: ReadingPlatform = ReadingPlatform.PAPER,
    val recordFormat: RecordFormat = RecordFormat.PAGE,
    val labels: MutableList<String> = mutableListOf(),
    val readingRecords: MutableList<ReadingRecord> = mutableListOf(),
    val readingNotes: MutableList<ReadingNote> = mutableListOf(),
) : BasicBook()

data class ReadingRecord(
    val startPage: Int = 0,
    val endPage: Int = 0,
    val timestamp: Timestamp = Timestamp.now()
)

data class ReadingNote(
    val note: String = "",
    val position: Int = 0,
    val timestamp: Timestamp = Timestamp.now()
)

enum class RecordFormat() {
    PAGE,
    PERCENTAGE,
}

enum class ReadingPlatform(val label: String, val icon: Int) {
    PAPER("Paper", R.drawable.ic_paper_book),
    PDF("PDF", R.drawable.ic_pdf),
    APPLE_BOOKS("Apple Book", R.drawable.ic_apple_book),
    WECHAT("WeChat Read", R.drawable.ic_wechat),
    KINDLE("Kindle", R.drawable.ic_kindle),
    GOOGLE("Google Book", R.drawable.ic_google_book),
    EBOOK("E Book", R.drawable.ic_ebook),
}

class ReadingBookFactory {
    companion object {
        fun buildSample(): ReadingBook {
            val records = mutableListOf(ReadingRecord(startPage = 10, endPage = 112))
            val notes = mutableListOf(ReadingNote("Hello world"))
            return ReadingBook(
                title = "Book Title ${Random.nextInt()}",
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
