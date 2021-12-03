package com.github.tinkzhang.readkeeper.search.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

@Composable
fun RkSearchErrorItem(
    error: Throwable,
    modifier: Modifier = Modifier.padding(16.dp)
) {
    // TODO: Use Outlined M3 version Card when it's available
    var errorMessage: String =
        when (error) {
            is IOException -> "Please check your network and retry"
            is HttpException -> {
                when(error.code()) {
                    HttpURLConnection.HTTP_BAD_REQUEST -> "Bad request"
                    HttpURLConnection.HTTP_CLIENT_TIMEOUT -> "Time out"
                    else -> "Network issue"
                }
            }
            else -> "Search failed. Please try again."
        }
    Text(text = errorMessage, modifier = modifier)
}