package app.tinks.readkeeper.homepage.weeklybook.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.tinks.readkeeper.basic.model.NYBookType
import app.tinks.readkeeper.basic.model.NYTimesBook
import kotlinx.coroutines.flow.Flow

@Dao
interface WeeklyBookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeeklyBooks(books: List<NYTimesBook>)

    @Query("DELETE FROM nytimesbook WHERE type = :type")
    suspend fun deleteAllBooks(type: NYBookType)

    @Query("SELECT * FROM nytimesbook WHERE type = :type")
    fun getAllBooks(type: NYBookType): Flow<List<NYTimesBook>>

    @Query("SELECT * FROM nytimesbook WHERE title = :title")
    fun getBook(title: String): Flow<NYTimesBook>
}