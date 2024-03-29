package app.tinks.readkeeper.basic.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nytimesbook")
@Keep
data class NYTimesBook(
    @PrimaryKey
    val title: String = "",
    @ColumnInfo
    val author: String = "",
    @ColumnInfo
    val bookImage: String = "",
    @ColumnInfo
    val amazonProductUrl: String? = null,
    @ColumnInfo
    val description: String? = null,
    @ColumnInfo
    val rank: Int = 0,
    @ColumnInfo
    val rankLastWeek: Int = 0,
    @ColumnInfo
    val weeksOnList: Int = 0,
    @ColumnInfo
    val type: NYBookType = NYBookType.Fictions
) {
    fun convertToBook(): Book = Book(
        basicInfo = BasicInfo(
            title = this.title,
            imageUrl = this.bookImage,
            author = this.author,
            amazonLink = this.amazonProductUrl,
        )
    )
}

fun NYTimesBookSample() = NYTimesBook(
    title = "INVISIBLE",
    author = "Danielle Steel",
    bookImage = "https://images-na.ssl-images-amazon.com/images/I/416Uc0RhQWL._SX327_BO1,204,203,200_.jpg",
    amazonProductUrl = "https://www.amazon.com/dp/198482158X?tag=NYTBSREV-20",
    description = "The daughter of a couple in a loveless marriage is discovered by a British filmmaker and thrust into the public eye.",
    rank = 1,
    rankLastWeek = 0,
    weeksOnList = 1,
)

enum class NYBookType {
    Fictions,
    NonFictions
}