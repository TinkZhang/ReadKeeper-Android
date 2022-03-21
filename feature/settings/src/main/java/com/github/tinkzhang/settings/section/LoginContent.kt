package com.github.tinkzhang.settings.section

import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.common.SignInButton


@ExperimentalMaterial3Api
@Composable
fun LoginContent(onLoginClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            AndroidView(factory = { context ->
                SignInButton(context).apply {
                    setSize(SignInButton.SIZE_WIDE)
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    setOnClickListener { onLoginClick() }
                }
            })
        }
    }
}
