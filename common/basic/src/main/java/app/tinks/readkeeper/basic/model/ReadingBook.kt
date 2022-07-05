package app.tinks.readkeeper.basic.model

import com.google.firebase.Timestamp
import kotlin.random.Random
import app.tinks.readkeeper.basic.R

data class ReadingBook(
    override val bookInfo: BookInfo = BookInfo(),
    override val category: String? = null,
    override val timeInfo: TimeInfo = TimeInfo(),
    val platform: ReadingPlatform = ReadingPlatform.GENERAL,
    val pageFormat: PageFormat = PageFormat.PAGE,
    val records: List<ReadingRecord> = listOf(),
    val formatEdited: Boolean = false,
    val archived: Boolean = false,
) : EditableBook {
    fun update(
        pages: Int = this.bookInfo.pages,
        category: String? = this.category,
        platform: ReadingPlatform = this.platform,
        pageFormat: PageFormat = this.pageFormat,
        records: List<ReadingRecord> = this.records,
        formatEdited: Boolean = this.formatEdited,
        archived: Boolean = this.archived,
    ): ReadingBook = this.copy(
        bookInfo = this.bookInfo.copy(pages = pages),
        timeInfo = this.timeInfo.copy(editedTime = Timestamp.now()),
        platform = platform,
        category = category,
        pageFormat = pageFormat,
        records = records,
        formatEdited = formatEdited,
        archived = archived,
    )
}

data class ReadingRecord(
    val startPage: Int = 0,
    val endPage: Int = 0,
    val note: String = "",
    val timestamp: Timestamp = Timestamp.now()
)

enum class PageFormat {
    PAGE,
    PERCENT_100,
    PERCENT_1000,
    PERCENT_10000
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
                    imageUrl = "https://img2.doubanio.com/view/subject/s/public/s34039232.jpg",
                    author = "Tink",
                    pages = 1688,
                    rating = 5.0,
                    pubYear = 2022,
                ),
                timeInfo = TimeInfo(
                    Timestamp.now(),
                    Timestamp.now()
                ),
                records = buildReadingRecords(),
                category = "Kotlin"
            )
        }

        fun buildReadingRecords(): List<ReadingRecord> {
            val list = mutableListOf<ReadingRecord>()
            for (i in 0..Random.nextInt(20)) {
                list.add(
                    ReadingRecord(
                        startPage = Random.nextInt(12, 21),
                        endPage = Random.nextInt(577, 689),
                        note = "this is a testing pfjdisjapiofjapiofjaiojdfioajfiosa"
                    )
                )
            }
            return list
        }
    }
}
