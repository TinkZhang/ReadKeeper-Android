package app.tinks.readkeeper.uicomponent.notelist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.tinks.readkeeper.basic.BookViewModel
import app.tinks.readkeeper.basic.model.BookFactory
import app.tinks.readkeeper.uicomponent.R
import app.tinks.readkeeper.uicomponent.cellview.BookNote

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListPage(
    bookViewModel: BookViewModel,
    uuid: String?,
    navController: NavController,
) {
    if (uuid.isNullOrEmpty()) return

    val records by bookViewModel.getRecords(uuid).collectAsState(initial = emptyList())
    val book by bookViewModel.getBook(uuid).collectAsState(initial = BookFactory.buildEmptyBook())
    val noteRecords = records.filterNot { it.note.isNullOrEmpty() }

    Scaffold(topBar = {
        SmallTopAppBar(title = { Text(stringResource(id = R.string.all_notes)) }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        })
    }) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(noteRecords) { record ->
                BookNote(
                    record = record,
                    pageFormat = book.pageFormat,
                    pages = book.realPages,
                    bigSize = true
                )
            }
        }
    }
}
