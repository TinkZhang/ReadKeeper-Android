package com.github.tinkzhang.search.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun SearchTextField(
    onSearch: (String) -> Unit = {},
) {
    var keyword by remember { mutableStateOf("") }

    val focusRequester = FocusRequester()
    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose { }
    }
    // TODO: replace to M3 when it's available
    androidx.compose.material3.Surface {
        OutlinedTextField(
            value = keyword,
            onValueChange = {
                keyword = it
            },
            singleLine = true,
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(keyword)
                }
            ),
            textStyle = MaterialTheme.typography.titleLarge,
            trailingIcon = {
                IconButton(onClick = { onSearch(keyword) }) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null
                    )
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                textColor = MaterialTheme.colorScheme.onSurface,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )
    }

}

@Preview
@Composable
private fun SearchTextFieldPreview() {
    SearchTextField()
}