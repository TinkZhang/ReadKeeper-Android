package app.tinks.readkeeper.basic.convertors

import app.tinks.readkeeper.basic.database.BookEntity
import app.tinks.readkeeper.basic.model.BasicInfo
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.TimeInfo
import com.google.firebase.Timestamp

fun Book.convertToBookEntity() = BookEntity(
    uuid = this.basicInfo.uuid,
    title = this.basicInfo.title,
    author = this.basicInfo.author,
    amazonLink = this.basicInfo.amazonLink,
    imageUrl = this.basicInfo.imageUrl,
    pages = this.basicInfo.pages,
    rating = this.basicInfo.rating,
    pubYear = this.basicInfo.pubYear,
    isbn = this.basicInfo.isbn,
    progress = this.progress,
    platform = this.platform,
    pageFormat = this.pageFormat,
    status = this.status,
    editedTime = this.timeInfo.editedTime.seconds,
    addedTime = this.timeInfo.addedTime.seconds
)

fun BookEntity.convertToBook() = Book(
    basicInfo = BasicInfo(
        uuid = this.uuid,
        title = this.title,
        imageUrl = this.imageUrl,
        author = this.author,
        pages = this.pages,
        rating = this.rating,
        pubYear = this.pubYear,
        amazonLink = this.amazonLink,
        isbn = this.isbn
    ),
    status = this.status,
    realPages = this.realPages,
    progress = this.progress,
    pageFormat = this.pageFormat,
    timeInfo = TimeInfo(
        addedTime = Timestamp(this.addedTime, 0),
        editedTime = Timestamp(this.editedTime, 0)
    )
)


