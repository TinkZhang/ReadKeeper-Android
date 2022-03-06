package com.github.tinkzhang.settings

import androidx.lifecycle.ViewModel
import com.github.tinkzhang.basic.DataStoreRepository
import com.github.tinkzhang.basic.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    val userRepository: UserRepository
) : ViewModel() {

}