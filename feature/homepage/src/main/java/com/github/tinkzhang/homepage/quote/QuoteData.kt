package com.github.tinkzhang.homepage.quote

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity
data class Quote(
    @PrimaryKey val uid: Int,
    @ColumnInfo val quote: String,
    @ColumnInfo val author: String?,
    )

@Dao
interface QuoteDao {
    @Query("SELECT * FROM Quote WHERE uid == 2")
    fun getRandom(): Flow<Quote>
}

@Database(entities = [Quote::class], version = 1)
abstract class QuoteDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
}

class QuoteRepository(context: Context) {
    private val db = Room
        .databaseBuilder(context, QuoteDatabase::class.java, "quote-database")
//        .createFromAsset("quote.db")
        .build()
    var quote: Flow<Quote> = db.quoteDao().getRandom()

    fun reset() {
        quote = db.quoteDao().getRandom()
    }
}