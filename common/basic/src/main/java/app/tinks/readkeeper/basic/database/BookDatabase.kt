package app.tinks.readkeeper.basic.database

import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Database(entities = [ReadingBookEntity::class], version = 1)
abstract class BookDatabase : RoomDatabase() {
    abstract fun readingBookDao(): ReadingBookDao
    var lastUpdated: Long = System.currentTimeMillis()
}

@Dao
interface ReadingBookDao {
    @Query("SELECT * FROM readingBookEntity")
    fun pagingSource(): PagingSource<Int, ReadingBookEntity>

    @Query("DELETE FROM readingBookEntity")
    suspend fun clearAll()

    @Delete
    suspend fun delete(book: ReadingBookEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(books: List<ReadingBookEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: ReadingBookEntity)

    @Query("SELECT * FROM readingBookEntity WHERE uuid == (:uuid)")
    fun query(uuid: String): Flow<List<ReadingBookEntity>>

}

@Entity
data class ReadingBookEntity(
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "title") val title: String
)

