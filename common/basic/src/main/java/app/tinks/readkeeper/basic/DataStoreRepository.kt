package app.tinks.readkeeper.basic

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Settings")

object DataStoreKey {
    const val THEME = "theme"
    const val IS_QUOTE_ENABLE = "quote"
}

@SuppressLint("StaticFieldLeak")
object DataStoreRepository {

    val context: Context = ReadApplication.getContext()!!

    suspend fun putString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    suspend fun putInt(key: String, value: Int) {
        val preferencesKey = intPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    fun getString(key: String): Flow<String?> {
        return try {
            val preferencesKey = stringPreferencesKey(key)
            val preferences = context.dataStore.data
            preferences.map { it[preferencesKey] }
        } catch (e: Exception) {
            e.printStackTrace()
            flowOf(null)
        }
    }

    fun getBoolean(key: String): Flow<Boolean?> {
        return try {
            val preferencesKey = booleanPreferencesKey(key)
            val preferences = context.dataStore.data
            preferences.map { it[preferencesKey] }
        } catch (e: Exception) {
            e.printStackTrace()
            flowOf(null)
        }
    }

    suspend fun updateString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { it[preferencesKey] = value }
    }

    suspend fun updateBoolean(key: String, value: Boolean) {
        val preferencesKey = booleanPreferencesKey(key)
        context.dataStore.edit { it[preferencesKey] = value }
    }

    suspend fun getInt(key: String): Int? {
        return try {
            val preferencesKey = intPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            preferences[preferencesKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
