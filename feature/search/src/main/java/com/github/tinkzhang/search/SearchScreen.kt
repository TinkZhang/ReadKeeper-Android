package com.github.tinkzhang.search

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.NorthWest
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

const val MAX_HISTORY_NUMBER = 8
const val HISTORY_BREAKER = "##"

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun SearchScreen(navController: NavController) {
    val searchHistory = stringPreferencesKey("history")
    val context = LocalContext.current
    val searchHistoryFlow: Flow<String> = context.dataStore.data.map {
        it[searchHistory] ?: ""
    }
    val historyString: String by searchHistoryFlow.collectAsState("")
    val historyItems = historyString.split(HISTORY_BREAKER).filter { it.isNotEmpty() }
    Column {
        val coroutineScope = rememberCoroutineScope()
        SmallTopAppBar(title = {
            RkSearchTextField(
                onSearch = { keyword ->
                    navController.navigate("search_result/${keyword}")
                    coroutineScope.launch {
                        context.dataStore.edit {
                            if (historyItems.size > MAX_HISTORY_NUMBER) {
                                it[searchHistory] = historyItems.subList(0, MAX_HISTORY_NUMBER - 1)
                                    .joinToString(separator = HISTORY_BREAKER, prefix = keyword)
                            } else {
                                it[searchHistory] = "$keyword$HISTORY_BREAKER$historyString"
                            }
                        }
                    }
                }
            )
        }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        })
        Divider(color = MaterialTheme.colorScheme.onBackground)
        historyItems.forEach {
            RkHistoryItem(
                history = it,
                onClick = { navController.navigate("search_result/${it}") })
        }
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

@Composable
fun RkHistoryItem(
    history: String,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(start = 32.dp, end = 32.dp, top = 16.dp, bottom = 16.dp)
            .fillMaxWidth()
    ) {
        Row {
            Icon(Icons.Default.History, contentDescription = null)
            Spacer(Modifier.width(16.dp))
            Text(history, maxLines = 1)
        }
        Icon(
            Icons.Default.NorthWest,
            contentDescription = null,
            Modifier.padding(start = 32.dp)
        )
    }
}

@Preview
@Composable
fun RkHistoryItemPrev() {
    RkHistoryItem(history = "Hello World")
}
