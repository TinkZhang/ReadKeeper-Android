package com.github.tinkzhang.readkeeper

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.tinkzhang.readkeeper.reading.ReadingViewModel
import com.github.tinkzhang.readkeeper.search.SearchViewModel


@Composable
fun HomeScreen(navController: NavController,
               searchViewModel: SearchViewModel,
               readingViewModel: ReadingViewModel,
) {
    var searchKeyword by remember {
        mutableStateOf("")
    }
    Column() {
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
        )
        Button(onClick = {
            readingViewModel.addBook()
            readingViewModel.getBook()
        }) {
           Text("Add Book")
        }
    }

}
