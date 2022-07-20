package app.tinks.readkeeper.uicomponent.editbook

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.tinks.readkeeper.basic.BookEditViewModel
import app.tinks.readkeeper.basic.model.BookFactory
import app.tinks.readkeeper.uicomponent.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookEditPage(
    uuid: String?, bookEditViewModel: BookEditViewModel, navController: NavController
) {
    if (uuid.isNullOrEmpty()) return
    var showDiscardConfirmationDialog by remember { mutableStateOf(false) }
    val book by bookEditViewModel.bookFlow.collectAsState(initial = BookFactory.buildEmptyBook())

    if (book.basicInfo.isbn == "1234567890123") return

    Scaffold(topBar = {
        SmallTopAppBar(title = { Text(text = stringResource(id = R.string.edit_book)) },
            modifier = Modifier.fillMaxWidth(),
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.back),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            })
    }, bottomBar = {
        FilledTonalIconButton(onClick = {
            bookEditViewModel.update()
            navController.popBackStack()
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(id = R.string.save))
        }
    }) { it ->
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            EditTitleField(bookTitle = book.basicInfo.title, modifier = Modifier.fillMaxWidth()) {
                bookEditViewModel.book = bookEditViewModel.book.copy(
                    basicInfo = bookEditViewModel.book.basicInfo.copy(title = it)
                )
            }

            EditPageField(pages = book.realPages,
                pageFormat = book.pageFormat,
                onPageFormatChange = {
                    bookEditViewModel.book = bookEditViewModel.book.copy(pageFormat = it)
                },
                onPageNumberChange = {
                    bookEditViewModel.book =
                        bookEditViewModel.book.copy(realPages = it.toIntOrNull() ?: 0)
                })

            PlatformField(platform = book.platform, modifier = Modifier.fillMaxWidth()) {
                bookEditViewModel.book = bookEditViewModel.book.copy(platform = it)
            }
        }
    }

    if (showDiscardConfirmationDialog) {
        AlertDialog(onDismissRequest = { showDiscardConfirmationDialog = false }, confirmButton = {
            TextButton(onClick = {
                showDiscardConfirmationDialog = true
                navController.popBackStack()
            }) {
                Text(stringResource(id = R.string.discard))
            }
        }, dismissButton = {
            TextButton(onClick = { showDiscardConfirmationDialog = true }) {
                Text(stringResource(id = R.string.keep))
            }
        }, text = { Text(stringResource(id = R.string.discard_edit_book_confirmation)) })
    }
}
