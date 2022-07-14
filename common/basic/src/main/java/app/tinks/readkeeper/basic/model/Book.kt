package app.tinks.readkeeper.basic.model

import app.tinks.readkeeper.basic.R
import com.google.firebase.Timestamp
import java.util.*

data class Book(
    val basicInfo: BasicInfo = BasicInfo(),
    val timeInfo: TimeInfo = TimeInfo(),
    val status: Status = Status.READING,
    val pageFormat: PageFormat = PageFormat.PAGE,
    val realPages: Int = basicInfo.pages,
    val platform: Platform? = null,
    val progress: Int = 0,
) {
    fun update(
        progress: Int = this.progress,
        status: Status = this.status,
        realPages: Int = this.realPages,
        pageFormat: PageFormat = this.pageFormat,
        platform: Platform? = this.platform,
    ) = this.copy(
        progress = progress,
        status = status,
        pageFormat = pageFormat,
        realPages = realPages,
        platform = platform,
        timeInfo = this.timeInfo.copy(editedTime = Timestamp.now())
    )
}

enum class PageFormat {
    PAGE,
    PERCENT_100,
    PERCENT_1000,
    PERCENT_10000
}

enum class Status {
    READING,
    WISH,
    ARCHIVED,
    SEARCH
}

enum class Platform(val label: String, val icon: Int) {
    PAPER("Paper", R.drawable.ic_paper_book),
    PDF("PDF", R.drawable.ic_pdf),
    APPLE_BOOKS("Apple Book", R.drawable.ic_apple_book),
    WECHAT("WeChat Read", R.drawable.ic_wechat),
    KINDLE("Kindle", R.drawable.ic_kindle),
    GOOGLE("Google Book", R.drawable.ic_google_book),
    GENERAL("Others", R.drawable.ic_other_book),
}

data class TimeInfo(
    val addedTime: Timestamp = Timestamp.now(),
    val editedTime: Timestamp = Timestamp.now()
)

data class BasicInfo(
    var title: String = "",
    var imageUrl: String = "",
    var author: String = "",
    var pages: Int = 0,
    var rating: Double = 0.0,
    var pubYear: Int = 0,
    val amazonLink: String? = null,
    val isbn: String? = null,
    val uuid: String = isbn + UUID.randomUUID().toString(),
    val description: String = "",
)
