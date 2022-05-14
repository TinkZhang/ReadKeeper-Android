package com.github.tinkzhang.search.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.github.tinkzhang.search.R

@Composable
fun SearchTextField(
    @StringRes placeHolderText: Int,
    onSearch: (String) -> Unit = {},
) {
    var keyword by remember { mutableStateOf("") }

    val focusRequester = FocusRequester()
    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose { }
    }
    OutlinedTextField(value = keyword,
        onValueChange = {
            keyword = it
        },
        singleLine = true,
        keyboardActions = KeyboardActions(onSearch = {
            onSearch(keyword)
        }),
        placeholder = { Text(stringResource(id = placeHolderText)) },
        textStyle = MaterialTheme.typography.titleLarge,
        trailingIcon = {
            IconButton(onClick = { onSearch(keyword) }) {
                Icon(
                    Icons.Default.Search, contentDescription = null
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

@Preview
@Composable
private fun SearchTextFieldPreview() {
    SearchTextField(placeHolderText = R.string.home_search_bar)
}