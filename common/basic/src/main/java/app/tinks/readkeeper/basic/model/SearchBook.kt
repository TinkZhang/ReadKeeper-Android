package app.tinks.readkeeper.basic.model

import kotlin.random.Random

data class SearchBook(
    override val bookInfo: BookInfo,
) : Book {
    fun convertToWishBook() = WishBook(bookInfo = bookInfo)
    fun convertToReadingBook() = ReadingBook(bookInfo = bookInfo)
}

class SearchBookFactory {
    companion object {
        fun buildSample(): SearchBook {
            return SearchBook(
                bookInfo = BookInfo(
                    title = "Book Title ${Random.nextInt()}",
                    imageUrl = "https://img2.doubanio.com/view/subject/s/public/s34039232.jpg",
                    author = "Tink",
                    pages = 1688,
                    rating = 5.0,
                    pubYear = 2022,
                ),
            )
        }
    }
}
