package com.github.tinkzhang.readkeeper

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.tinkzhang.readkeeper.reading.ReadingViewModel
import com.github.tinkzhang.readkeeper.search.SearchViewModel


@Composable
fun HomeScreen(
    navController: NavController,
    searchViewModel: SearchViewModel,
    readingViewModel: ReadingViewModel,
) {
    var searchKeyword by remember {
        mutableStateOf("")
    }
    Column() {
        // TODO: Update to M3 version TextField when it's available
        OutlinedTextField(
            value = searchKeyword,
            onValueChange = { text -> searchKeyword = text },
            singleLine = true,
            placeholder = { Text(text = stringResource(id = R.string.search_hint)) },
            leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = "Search") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions {
                searchViewModel.searchBook(searchKeyword)
                navController.navigate("search/${searchKeyword}") {
                    launchSingleTop = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
            )
        )
        Button(onClick = {
            readingViewModel.addBook()
        }) {
            Text("Add Book")
        }

        Button(onClick = {
            readingViewModel.syncList()
        }) {
            Text("Get Book")
        }
    }

}
