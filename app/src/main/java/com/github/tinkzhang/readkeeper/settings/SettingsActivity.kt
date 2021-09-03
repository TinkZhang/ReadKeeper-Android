package com.github.tinkzhang.readkeeper.settings

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.github.tinkzhang.readkeeper.R
import com.github.tinkzhang.readkeeper.SettingsScreen
import com.github.tinkzhang.readkeeper.user.UserViewModel

class SettingsActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            title = getString(R.string.settings)
            setDisplayHomeAsUpEnabled(true)
        }
        setContent {
            SettingsScreen(userViewModel)
        }
    }
}