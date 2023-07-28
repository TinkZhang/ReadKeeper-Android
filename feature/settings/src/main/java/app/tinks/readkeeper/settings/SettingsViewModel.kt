package app.tinks.readkeeper.settings

import android.content.Context
import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import app.tinks.readkeeper.basic.DataStoreKey
import app.tinks.readkeeper.basic.DataStoreRepository
import app.tinks.readkeeper.basic.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsViewModel : ViewModel() {
    private val dataStoreRepository = DataStoreRepository
    private val userRepository = UserRepository
    var themeStatus: Flow<ThemeStatus> = dataStoreRepository.getString(DataStoreKey.THEME).map {
        ThemeStatus.valueOf(it ?: ThemeStatus.DEFAULT.name)
    }

    var isQuoteEnabled: Flow<Boolean> =
        dataStoreRepository.getBoolean(DataStoreKey.IS_QUOTE_ENABLE).map {
            it ?: true
        }

    val loginStatus = userRepository.loginStatus

    val profileImageUrl
        get() = userRepository.user?.photoUrl.toString()
    val username
        get() = userRepository.user?.displayName ?: ""
    val userEmail
        get() = userRepository.user?.email ?: ""

    fun signOut() {
        userRepository.signOutWithGoogle()
    }

    fun signIn(context: Context) {
        userRepository.signInWithGoogle(context)
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

enum class ThemeStatus(@StringRes val label: Int) {
    DARK(R.string.dark), LIGHT(R.string.light), DEFAULT(R.string.system_default)
}
