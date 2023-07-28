package app.tinks.readkeeper.basic

import app.tinks.readkeeper.basic.convertors.convertToBook
import app.tinks.readkeeper.basic.convertors.convertToBookEntity
import app.tinks.readkeeper.basic.convertors.convertToRecordEntity
import app.tinks.readkeeper.basic.database.BookEntity
import app.tinks.readkeeper.basic.database.RecordEntity
import app.tinks.readkeeper.basic.database.bookDatabase
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.Record
import app.tinks.readkeeper.basic.model.Status
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

object BookRepository {
    private val database = bookDatabase
    private val userRepository = UserRepository
    suspend fun sync() {
        if (userRepository.isLogged.value == false) return
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

    suspend fun addBook(book: Book) {
        bookDao.insert(book.convertToBookEntity())
        if (UserRepository.isLogged.value == true) {
            userRepository.add(book)
        }
    }

    suspend fun addRecord(record: Record) {
        recordDao.insert(record.convertToRecordEntity())
        if (UserRepository.isLogged.value == true) {
            userRepository.add(record)
        }
    }

    suspend fun move(book: Book, status: Status) {
        bookDao.update(
            book.convertToBookEntity().copy(
                status = status, addedTime = Timestamp.now().seconds
            )
        )
        if (UserRepository.isLogged.value == true) {
            userRepository.update(book.copy(status = status))
        }
    }

    suspend fun update(book: Book) {
        bookDao.update(book.convertToBookEntity())
        if (UserRepository.isLogged.value == true) {
            userRepository.update(book)
        }
    }

    suspend fun deleteBook(uuid: String) {
        bookDao.delete(uuid)
        if (UserRepository.isLogged.value == true) {
            userRepository.remove(uuid)
        }
    }

    suspend fun deleteRecord(id: String) {
        recordDao.delete(id)
        userRepository.removeRecord(id)
    }

    suspend fun updateRecord(record: Record) {
        recordDao.update(record.convertToRecordEntity())
        if (UserRepository.isLogged.value == true) {
            userRepository.updateRecord(record)
        }
    }

    fun getBook(uuid: String): Flow<List<BookEntity>> = database.bookDao().query(uuid)

    fun getRecord(uuid: String): Flow<List<RecordEntity>> = database.recordDao().query(uuid)

    fun getAllRecords(): Flow<List<RecordEntity>> = database.recordDao().queryAll()

    fun getFirstReading(): Flow<List<BookEntity>> = database.bookDao().firstReading()
}