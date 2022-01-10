package com.github.tinkzhang.wish

import com.github.tinkzhang.basic.BaseViewModel
import com.github.tinkzhang.basic.UserRepository
import com.github.tinkzhang.basic.model.WishBook

class WishViewModel(override val localList: MutableSet<WishBook> = mutableSetOf()) :
    BaseViewModel<WishBook>(WishDataSource(UserRepository)) {
}