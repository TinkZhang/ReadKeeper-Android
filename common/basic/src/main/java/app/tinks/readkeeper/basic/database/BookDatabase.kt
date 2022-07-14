package app.tinks.readkeeper.basic.database

import androidx.paging.PagingSource
import androidx.room.*
import app.tinks.readkeeper.basic.model.PageFormat
import app.tinks.readkeeper.basic.model.Platform
import app.tinks.readkeeper.basic.model.Status
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow


@Database(entities = [BookEntity::class, RecordEntity::class], version = 1)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun recordDao(): RecordDao
    var lastUpdated: Long = System.currentTimeMillis()
}

@Dao
interface BookDao {
    @Query("SELECT * FROM bookEntity WHERE status == (:status) ")
    fun pagingSource(status: Status): PagingSource<Int, BookEntity>

    @Query("DELETE FROM bookEntity")
    suspend fun clearAll()

    @Query("DELETE FROM bookEntity WHERE uuid == (:uuid) ")
    suspend fun delete(uuid: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(books: List<BookEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: BookEntity)

    @Query("SELECT * FROM bookEntity WHERE uuid == (:uuid)")
    fun query(uuid: String): Flow<List<BookEntity>>

    @Query("SELECT * FROM bookEntity ORDER BY uuid LIMIT 1")
    suspend fun first(): List<BookEntity>

    @Query("SELECT * FROM bookEntity")
    suspend fun getAllBooks(): List<BookEntity>

    @Update
    suspend fun update(book: BookEntity)

}

@Dao
interface RecordDao {
    @Query("SELECT * FROM recordEntity WHERE uuid == (:uuid) ORDER BY timeStamp")
    fun query(uuid: String): Flow<List<RecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recordEntity: RecordEntity)

    @Query("DELETE FROM recordEntity WHERE id == (:id)")
    suspend fun delete(id: Int)
}

@Entity
data class BookEntity(
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "amazonLink") val amazonLink: String?,
    @ColumnInfo(name = "imageUrl") val imageUrl: String,
    @ColumnInfo(name = "status") val status: Status,
    @ColumnInfo(name = "isbn") val isbn: String? = null,
    @ColumnInfo(name = "pages") val pages: Int = 0,
    @ColumnInfo(name = "pubYear") val pubYear: Int = 1900,
    @ColumnInfo(name = "rating") val rating: Double = 0.0,
    @ColumnInfo(name = "progress") val progress: Int = 0,
    @ColumnInfo(name = "platform") val platform: Platform? = null,
    @ColumnInfo(name = "pageFormat") val pageFormat: PageFormat = PageFormat.PAGE,
    @ColumnInfo(name = "realPages") val realPages: Int = 0,
    @ColumnInfo(name = "addedTime") val addedTime: Long = Timestamp.now().seconds,
    @ColumnInfo(name = "editedTime") val editedTime: Long = Timestamp.now().seconds,
)

@Entity
data class RecordEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "uuid") val uuid: String,
    @ColumnInfo(name = "startPage") val startPage: Int,
    @ColumnInfo(name = "endPage") val endPage: Int,
    @ColumnInfo(name = "note") val note: String?,
    @ColumnInfo(name = "timeStamp") val timestamp: Long
)

