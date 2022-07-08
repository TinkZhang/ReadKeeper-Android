package app.tinks.readkeeper.reading

import app.tinks.readkeeper.basic.convertors.map
import app.tinks.readkeeper.basic.database.BookDatabase
import app.tinks.readkeeper.basic.database.ReadingBookEntity
import app.tinks.readkeeper.basic.model.ReadingBook
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadingRepository @Inject constructor(
    private val bookDatabase: BookDatabase
) {
    suspend fun addReadingBook(book: ReadingBook) {
        bookDatabase.readingBookDao().insert(book map ReadingBookEntity::class)
    }

    suspend fun deleteBook(book: ReadingBook) {
        bookDatabase.readingBookDao().delete(book map ReadingBookEntity::class)
    }

    fun getBook(uuid: String): Flow<List<ReadingBookEntity>> =
        bookDatabase.readingBookDao().query(uuid)

}