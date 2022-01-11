package com.github.readkeeper.archived

import com.github.tinkzhang.basic.BaseViewModel
import com.github.tinkzhang.basic.UserRepository
import com.github.tinkzhang.basic.model.ArchivedBook

class ArchivedViewModel(override val localList: MutableSet<ArchivedBook> = mutableSetOf()) :
    BaseViewModel<ArchivedBook>(ArchivedDataSource(UserRepository))
