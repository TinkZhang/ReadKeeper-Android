package com.github.tinkzhang.basic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.github.tinkzhang.basic.model.EditableBook

const val PAGE_SIZE = 10

abstract class BaseViewModel<T: EditableBook>(pagingSource: PagingSource<Int, T>): ViewModel() {
    abstract val localList: MutableSet<T>
    val flow = Pager(
        PagingConfig(pageSize = PAGE_SIZE)
    ) {
        pagingSource
    }.flow.cachedIn(viewModelScope)

    inline fun <reified T: EditableBook> addBook(book: T) {
        UserRepository.addBook(book)
    }

    fun addLocalList(book: T) {
        localList.add(book)
    }

    fun resetLocalList() {
        localList.clear()
    }

    fun updateLocalList(book: T) {
        localList.removeIf { it.bookInfo.uuid == book.bookInfo.uuid }
        localList.add(book)
    }

    fun getBook(uuid: String) = localList.first { it.bookInfo.uuid == uuid }

}