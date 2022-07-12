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

        fun buildArchivedSample(): Book {
            return Book(
                basicInfo = buildBasicInfo(),
                status = Status.ARCHIVED
            )
        }

        fun buildWishSample(): Book {
            return Book(
                basicInfo = buildBasicInfo(),
                status = Status.WISH
            )
        }

        fun buildEmptyBook() = Book(status = Status.READING)

        private fun buildBasicInfo() = BasicInfo(
            title = "Book Title ${Random.nextInt()}",
            imageUrl = "https://img2.doubanio.com/view/subject/s/public/s34039232.jpg",
            author = "Tink Zhang",
            pages = 1688,
            rating = 4.3,
            pubYear = 2022,
        )
    }
}