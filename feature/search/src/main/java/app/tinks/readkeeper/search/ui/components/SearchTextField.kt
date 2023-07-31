package app.tinks.readkeeper.search.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import app.tinks.readkeeper.search.R
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme

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
    TextField(
        value = keyword,
        onValueChange = {
            keyword = it
        },
        singleLine = true,
        keyboardActions = KeyboardActions(onSearch = {
            onSearch(keyword.ifBlank { "hello" })
        }),
        placeholder = { Text(stringResource(id = placeHolderText)) },
        textStyle = MaterialTheme.typography.titleLarge,
        trailingIcon = {
            IconButton(onClick = { onSearch(keyword.ifEmpty { "hello" }) }) {
                Icon(
                    Icons.Default.Search, contentDescription = "Search"
                )
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        )
    )
}

@PreviewAnnotation
@Composable
private fun SearchTextFieldPreview() {
    ReadKeeperTheme {
        Surface {
            SearchTextField(placeHolderText = R.string.home_search_bar)
        }
    }
}
