package com.github.tinkzhang.basic.model

import com.google.firebase.Timestamp
import kotlin.random.Random

data class ArchivedBook(
    override val bookInfo: BookInfo = BookInfo(),
    override val category: String? = null,
    override val timeInfo: TimeInfo = TimeInfo(),
    val platform: ReadingPlatform = ReadingPlatform.GENERAL,
    val pageFormat: PageFormat = PageFormat.PAGE,
    val records: List<ReadingRecord> = listOf(),
    val formatEdited: Boolean = false,
) : EditableBook {
    fun update(
        pages: Int = this.bookInfo.pages,
        category: String? = this.category,
        platform: ReadingPlatform = this.platform,
        pageFormat: PageFormat = this.pageFormat,
        records: List<ReadingRecord> = this.records,
        formatEdited: Boolean = this.formatEdited,
    ): ArchivedBook = this.copy(
        bookInfo = this.bookInfo.copy(pages = pages),
        timeInfo = this.timeInfo.copy(editedTime = Timestamp.now()),
        platform = platform,
        category = category,
        pageFormat = pageFormat,
        records = records,
        formatEdited = formatEdited,
    )
}

class ArchivedBookFactory {
    companion object {
        fun buildSample(): ArchivedBook {
            return ArchivedBook(
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

        private fun buildReadingRecords(): List<ReadingRecord> {
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
