package app.github.readkeeper.archived

import app.tinks.readkeeper.basic.BaseViewModel
import app.tinks.readkeeper.basic.UserRepository
import app.tinks.readkeeper.basic.model.ArchivedBook

class ArchivedViewModel(override val localList: MutableSet<ArchivedBook> = mutableSetOf()) :
    BaseViewModel<ArchivedBook>(ArchivedDataSource(UserRepository))
