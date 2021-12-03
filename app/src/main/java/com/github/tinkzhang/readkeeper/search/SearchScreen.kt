package com.github.tinkzhang.readkeeper.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import com.github.tinkzhang.readkeeper.common.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Composable
fun SearchScreen(navController: NavController) {
    val searchHistory = stringPreferencesKey("history")
    val searchHistoryFlow: Flow<String> = LocalContext.current.dataStore.data.map {
        it[searchHistory] ?: ""
    }

    Column() {
        SmallTopAppBar(title = {
            RkSearchTextField(
                onSearch = { keyword ->
                    navController.navigate("search_result/${keyword}")
                }
            )
        }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        })
        Text("hello")
    }
}

@Composable
fun RkSearchTextField(
    onSearch: (String) -> Unit,
) {
    var keyword by remember { mutableStateOf("") }

    val focusRequester = FocusRequester()
    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose { }
    }
    // TODO: replace to M3 when it's available
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
            unfocusedBorderColor = Color.Transparent
        )
    )
}