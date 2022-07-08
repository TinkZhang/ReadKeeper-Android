@file:Suppress("UNCHECKED_CAST")

package app.tinks.readkeeper.basic.convertors

import app.tinks.readkeeper.basic.database.ReadingBookEntity
import app.tinks.readkeeper.basic.model.BookInfo
import app.tinks.readkeeper.basic.model.ReadingBook
import app.tinks.readkeeper.basic.model.WishBook
import kotlin.reflect.KClass

infix fun <T : Any> ReadingBook.map(type: KClass<T>): T = when (type) {
    ReadingBookEntity::class -> ReadingBookEntity(
        this.bookInfo.uuid,
        title = this.bookInfo.title
    ) as T
    WishBook::class -> WishBook(
        bookInfo = this.bookInfo,
        category = this.category,
        timeInfo = this.timeInfo
    ) as T
    else -> throw(IllegalArgumentException("ReadingBook cannot map to ${type.simpleName}"))
}

infix fun <T : Any> ReadingBookEntity.map(type: KClass<T>): T = when (type) {
    ReadingBook::class -> ReadingBook(
        bookInfo = BookInfo(title = this.title, uuid = this.uuid)
    ) as T
    else -> throw(IllegalArgumentException("ReadingBookEntity cannot map to ${type.simpleName}"))
}
