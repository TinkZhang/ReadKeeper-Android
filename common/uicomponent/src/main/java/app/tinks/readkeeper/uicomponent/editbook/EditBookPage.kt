package app.tinks.readkeeper.uicomponent.editbook

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.uicomponent.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBookPage(
    book: Book,
    navController: NavController,
) {
    var showDiscardConfirmationDialog by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.edit_book)) },
                modifier = Modifier.fillMaxWidth(),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = stringResource(id = R.string.back),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        },
        bottomBar = {
            FilledTonalIconButton(onClick = { /*TODO*/ }) {
                Text(text = stringResource(id = R.string.save))
            }
        }
    ) { it ->
        Column(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            var title by remember { mutableStateOf(book.basicInfo.title) }
            EditTitleField(bookTitle = title) {
                title = it
            }

            var pageFormat by remember { mutableStateOf(book.pageFormat) }
            var page by remember { mutableStateOf(book.realPages) }
            EditPageField(
                pages = page,
                pageFormat = pageFormat,
                onPageFormatChange = { pageFormat = it },
                onPageNumberChange = { page = it.toIntOrNull() ?: 0 }
            )

            var platform by remember{ mutableStateOf(book.platform) }
            PlatformField(platform = platform) {
                platform = it
            }
        }
    }

    if (showDiscardConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showDiscardConfirmationDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showDiscardConfirmationDialog = true
                    navController.popBackStack()
                }) {
                    Text(stringResource(id = R.string.discard))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDiscardConfirmationDialog = true }) {
                    Text(stringResource(id = R.string.keep))
                }
            },
            text = { Text(stringResource(id = R.string.discard_edit_book_confirmation)) }
        )
    }
}

