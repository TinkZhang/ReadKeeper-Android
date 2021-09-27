package com.github.tinkzhang.readkeeper.reading

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.tinkzhang.readkeeper.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ReadingViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val readingBooks: MutableLiveData<ReadingBook> by lazy {
        MutableLiveData<ReadingBook>()
    }

    fun addBook(book: ReadingBook = ReadingBookFactory.buildSample()) {
        userRepository.addReadingBook(book)
    }

    fun getBook() {
        userRepository.getReadingBook()
    }


}
