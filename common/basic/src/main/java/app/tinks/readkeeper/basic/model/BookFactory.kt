package app.tinks.readkeeper.basic.model

import kotlin.random.Random

class BookFactory {
    companion object {
        fun buildSearchSample(): Book {
            return Book(
                basicInfo = buildBasicInfo(),
                status = Status.SEARCH
            )
        }

        fun buildReadingSample(): Book {
            return Book(
                basicInfo = buildBasicInfo(),
                status = Status.READING
            )
        }

        fun buildEmptyBook() = Book(status = Status.READING)

        private fun buildBasicInfo() = BasicInfo(
            title = "Book Title ${Random.nextInt()}",
            imageUrl = "https://img2.doubanio.com/view/subject/s/public/s34039232.jpg",
            author = "Tink",
            pages = 1688,
            rating = 5.0,
            pubYear = 2022,
        )
    }
}