package app.tinks.readkeeper.basic.model

import com.google.firebase.Timestamp
import kotlin.random.Random

data class WishBook(
    override val bookInfo: BookInfo = BookInfo(),
    override val category: String? = null,
    override val timeInfo: TimeInfo = TimeInfo()
) : EditableBook {
    fun convertToReadingBook() =
        ReadingBook(bookInfo = bookInfo, category = category)
}

class WishBookFactory {
    companion object {
        fun buildSample(): WishBook {
            return WishBook(
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
                category = "Kotlin"
            )
        }
    }
}