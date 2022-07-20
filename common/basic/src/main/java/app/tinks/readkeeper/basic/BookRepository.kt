package app.tinks.readkeeper.basic

import app.tinks.readkeeper.basic.convertors.convertToBook
import app.tinks.readkeeper.basic.convertors.convertToBookEntity
import app.tinks.readkeeper.basic.convertors.convertToRecordEntity
import app.tinks.readkeeper.basic.database.BookDatabase
import app.tinks.readkeeper.basic.database.BookEntity
import app.tinks.readkeeper.basic.database.RecordEntity
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.Record
import app.tinks.readkeeper.basic.model.Status
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val database: BookDatabase, private val userRepository: UserRepository
) {
    suspend fun sync() {
        val firstLocalBookEditedTime =
            database.bookDao().first().firstOrNull()?.convertToBook()?.timeInfo?.editedTime?.seconds
                ?: 0
        val firstRemoteBookEditedTime =
            userRepository.getFirstBook()?.timeInfo?.editedTime?.seconds ?: 0
        Timber.d("FirstLocalTime is $firstLocalBookEditedTime, and firstRemoteTime is $firstRemoteBookEditedTime")
        if (firstLocalBookEditedTime > firstRemoteBookEditedTime) {
            userRepository.add(database.bookDao().getAllBooks().map { it.convertToBook() })
        } else if (firstLocalBookEditedTime < firstRemoteBookEditedTime) {
            database.bookDao().insert(userRepository.getAllBooks().map { it.convertToBookEntity() })
        }
    }

    private val bookDao = database.bookDao()
    private val recordDao = database.recordDao()

    suspend fun add(book: Book) {
        bookDao.insert(book.convertToBookEntity())
        userRepository.add(book)
    }

    suspend fun add(record: Record) {
        recordDao.insert(record.convertToRecordEntity())
        userRepository.add(record)
    }

    suspend fun move(book: Book, status: Status) {
        bookDao.update(
            book.convertToBookEntity().copy(
                status = status,
                addedTime = Timestamp.now().seconds
            )
        )
    }

    suspend fun update(book: Book) {
        bookDao.update(book.convertToBookEntity())
    }

    suspend fun delete(uuid: String) {
        database.bookDao().delete(uuid)
        userRepository.remove(uuid)
    }

    fun getBook(uuid: String): Flow<List<BookEntity>> = database.bookDao().query(uuid)

    fun getRecord(uuid: String): Flow<List<RecordEntity>> = database.recordDao().query(uuid)
}