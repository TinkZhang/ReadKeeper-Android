package com.github.tinkzhang.readkeeper

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.tinkzhang.readkeeper.reading.ReadingViewModel
import com.github.tinkzhang.readkeeper.ui.SCREEN_ROUTE


@Composable
fun HomeScreen(
    navController: NavController,
    readingViewModel: ReadingViewModel,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // TODO: Update to M3 version TextField when it's available
//        OutlinedTextField(
//            value = searchKeyword,
//            onValueChange = { text -> searchKeyword = text },
//            singleLine = true,
//            placeholder = { Text(text = stringResource(id = R.string.search_hint)) },
//            leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = "Search") },
//            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
//            keyboardActions = KeyboardActions {
//                searchViewModel.searchBook(searchKeyword)
//                navController.navigate("search/${searchKeyword}") {
//                    launchSingleTop = true
//                }
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
//                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
//                focusedBorderColor = MaterialTheme.colorScheme.primary,
//            )
//        )
        FilledTonalButton(onClick = { navController.navigate(SCREEN_ROUTE.SEARCH)}, modifier = Modifier.padding(16.dp).fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search"
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text("Search by Google Books™")
            }
        }

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
