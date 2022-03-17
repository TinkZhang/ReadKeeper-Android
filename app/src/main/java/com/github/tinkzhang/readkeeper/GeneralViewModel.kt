package com.github.tinkzhang.readkeeper

import androidx.lifecycle.ViewModel
import com.github.tinkzhang.basic.DataStoreRepository
import com.github.tinkzhang.settings.ThemeStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GeneralViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository
): ViewModel(){
    var isDark: Flow<Boolean?> = dataStoreRepository.getString("theme").map {
        Timber.d("Get theme as $it")
        when(it){
            ThemeStatus.LIGHT.name.uppercase() -> false
            ThemeStatus.DARK.name.uppercase() -> true
            else -> null
        }
    }
}