package com.github.tinkzhang.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import com.github.tinkzhang.basic.DataStoreKey
import com.github.tinkzhang.basic.DataStoreRepository
import com.github.tinkzhang.basic.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    val userRepository: UserRepository
) : ViewModel() {

    var themeStatus: Flow<ThemeStatus> = dataStoreRepository.getString(DataStoreKey.THEME).map {
        ThemeStatus.valueOf(it ?: ThemeStatus.DEFAULT.name)
    }

    var isQuoteEnabled: Flow<Boolean> =
        dataStoreRepository.getBoolean(DataStoreKey.IS_QUOTE_ENABLE).map {
            it ?: true
        }

    suspend fun saveSetting(key: String, value: String) {
        Log.d("", "Update $key with $value")
        dataStoreRepository.updateString(key, value)
    }

    suspend fun saveSetting(key: String, value: Boolean) {
        Log.d("", "Update $key with $value")
        dataStoreRepository.updateBoolean(key, value)
    }
}

enum class ThemeStatus(val label: String) {
    DARK("Dark"), LIGHT("Light"), DEFAULT("System default")
}