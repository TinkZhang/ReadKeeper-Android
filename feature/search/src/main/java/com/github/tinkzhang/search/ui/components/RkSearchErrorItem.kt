package com.github.tinkzhang.search.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

@ExperimentalMaterial3Api
@Composable
fun RkSearchErrorItem(
    error: Throwable,
    modifier: Modifier = Modifier,
) {
    val errorMessage: String =
        when (error) {
            is IOException -> "Something went wrong."
            is HttpException -> {
                when (error.code()) {
                    HttpURLConnection.HTTP_BAD_REQUEST -> "Bad request."
                    HttpURLConnection.HTTP_CLIENT_TIMEOUT -> "Time out."
                    else -> "Network issue."
                }
            }
            else -> "Search failed."
        }
    OutlinedCard(
        modifier = modifier
            .padding(32.dp)
            .fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.errorContainer
    ) {
        Column(
            modifier
                .padding(32.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Please tap here to try again.",
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.height(32.dp))
            Icon(Icons.Default.Refresh, null)
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun RkSearchErrorItemPreview() {
    RkSearchErrorItem(error = IOException(""))
}