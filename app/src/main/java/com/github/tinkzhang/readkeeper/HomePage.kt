package com.github.tinkzhang.readkeeper

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.github.tinkzhang.readkeeper.search.SearchViewModel
import timber.log.Timber


@Composable
fun HomePage(navController: NavController, searchViewModel: SearchViewModel) {
    var searchKeyword by remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        value = searchKeyword,
        onValueChange = { text -> searchKeyword = text },
        singleLine = true,
        placeholder = { Text(text = stringResource(id = R.string.search_hint)) },
        trailingIcon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
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
}
