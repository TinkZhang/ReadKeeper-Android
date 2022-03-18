package com.github.tinkzhang.settings

import android.util.Log
import androidx.lifecycle.ViewModel
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

    var themeStatus: Flow<ThemeStatus> = dataStoreRepository.getString("theme").map {
        ThemeStatus.valueOf(it ?: ThemeStatus.DEFAULT.name)
    }

    suspend fun saveSetting(key: String, value: String) {
        Log.d("", "Update $key with $value")
        dataStoreRepository.updateString(key, value)
    }
}

enum class ThemeStatus(val label: String) {
    DARK("Dark"), LIGHT("Light"), DEFAULT("System default")
}
