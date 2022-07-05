package app.tinks.readkeeper

import androidx.lifecycle.ViewModel
import app.github.tinkzhang.settings.ThemeStatus
import app.tinks.readkeeper.basic.DataStoreKey
import app.tinks.readkeeper.basic.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GeneralViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository
): ViewModel(){
    var isDark: Flow<Boolean?> = dataStoreRepository.getString(DataStoreKey.THEME).map {
        Timber.d("Get theme as $it")
        when(it){
            ThemeStatus.LIGHT.name.uppercase() -> false
            ThemeStatus.DARK.name.uppercase() -> true
            else -> null
        }
    }
}