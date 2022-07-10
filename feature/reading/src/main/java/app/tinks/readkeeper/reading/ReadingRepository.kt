package app.tinks.readkeeper.reading

//class ReadingRepository @Inject constructor(
//    private val bookDatabase: BookDatabase
//) {
//    suspend fun addReadingBook(book: ReadingBook) {
//        bookDatabase.readingBookDao().insert(book map BookEntity::class)
//    }
//
//    fun getBook(uuid: String): Flow<List<BookEntity>> =
//        bookDatabase.readingBookDao().query(uuid)
//
//}