package app.tinks.readkeeper

import androidx.lifecycle.ViewModel
import app.tinks.readkeeper.basic.DataStoreKey
import app.tinks.readkeeper.basic.DataStoreRepository
import app.tinks.readkeeper.settings.ThemeStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class GeneralViewModel : ViewModel() {
    var isDark: Flow<Boolean?> = DataStoreRepository.getString(DataStoreKey.THEME).map {
        Timber.d("Get theme as $it")
        when (it) {
            ThemeStatus.LIGHT.name.uppercase() -> false
            ThemeStatus.DARK.name.uppercase() -> true
            else -> null
        }
    }
}