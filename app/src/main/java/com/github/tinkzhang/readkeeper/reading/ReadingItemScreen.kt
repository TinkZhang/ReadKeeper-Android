package com.github.tinkzhang.readkeeper.reading

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DataSaverOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.github.tinkzhang.readkeeper.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingItemScreen(
    uuid: String,
    readingViewModel: ReadingViewModel,
    navController: NavController
) {
    val book = readingViewModel.getBook(uuid)
        Scaffold(topBar = {
            MediumTopAppBar(title = { Text( book.bookInfo.title) }, navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.back),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            })
        }, floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Progress") },
                onClick = { /*TODO*/ },
                icon = {
                    Icon(
                        Icons.Default.DataSaverOn,
                        contentDescription = "Add Reading Progress"
                    )
                })
        }) {

        }

}