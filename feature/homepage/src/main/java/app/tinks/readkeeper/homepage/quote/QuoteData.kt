package app.tinks.readkeeper.homepage.quote

import android.content.Context
import androidx.annotation.Keep
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlin.random.Random

@Keep
@Entity
data class Quote(
    @PrimaryKey val uid: Int,
    @ColumnInfo val quote: String,
    @ColumnInfo val author: String?,
)

@Dao
interface QuoteDao {
    @Query("SELECT * FROM Quote WHERE uid == :id")
    fun getQuote(id: Int): Flow<Quote>
}

@Database(entities = [Quote::class], version = 1)
abstract class QuoteDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
}

class QuoteRepository(context: Context) {
    private val db = Room
        .databaseBuilder(context, QuoteDatabase::class.java, "quote-database")
        .createFromAsset("quote.db")
        .build()
    private val randomId = Random.nextInt(from = 1, until = 106)

    fun getQuote(id: Int): Flow<Quote> = db.quoteDao().getQuote(id)
}