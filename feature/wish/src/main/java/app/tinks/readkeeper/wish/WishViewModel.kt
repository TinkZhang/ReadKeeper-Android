package app.tinks.readkeeper.wish

import app.tinks.readkeeper.basic.BaseViewModel
import app.tinks.readkeeper.basic.UserRepository
import app.tinks.readkeeper.basic.model.WishBook

class WishViewModel(override val localList: MutableSet<WishBook> = mutableSetOf()) :
    BaseViewModel<WishBook>(WishDataSource(UserRepository)) {
    fun moveToReading(book: WishBook) {
        localList.remove(book)
        UserRepository.removeWishBook(book.bookInfo.uuid)
        UserRepository.addBook(book.convertToReadingBook())
    }
    fun moveToReading(uuid: String) {
        moveToReading(this.getBook(uuid))
    }
}