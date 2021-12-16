package com.github.tinkzhang.readkeeper.common.data

import com.github.tinkzhang.readkeeper.R
import com.google.firebase.Timestamp
import kotlin.random.Random

data class ReadingBook(
    override val bookInfo: BookInfo = BookInfo(),
    override val category: String? = null,
    override val timeInfo: TimeInfo = TimeInfo(),
    val platform: ReadingPlatform = ReadingPlatform.GENERAL,
    val pageFormat: PageFormat = PageFormat.PAGE,
    val records: List<ReadingRecord> = listOf(),
    val notes: List<ReadingNote> = listOf(),
    val archived: Boolean = false,
) : EditableBook

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

enum class PageFormat {
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
    GENERAL("Others", R.drawable.ic_other_book),
}

class ReadingBookFactory {
    companion object {
        fun buildSample(): ReadingBook {
            return ReadingBook(
                bookInfo = BookInfo(
                    title = "Book Title ${Random.nextInt()}",
                    imageUrl = "http://books.google.com/books/content?id=gK98gXR8onwC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                    author = "Tink",
                    pages = 1688,
                    rating = 5.0,
                    pubYear = 2022,
                ),
                timeInfo = TimeInfo(
                    Timestamp.now(),
                    Timestamp.now()
                ),
                category = "Kotlin"
            )
        }
    }
}
