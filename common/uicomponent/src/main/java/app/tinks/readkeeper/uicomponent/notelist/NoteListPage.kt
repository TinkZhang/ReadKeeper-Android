package app.tinks.readkeeper.uicomponent.notelist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import app.tinks.readkeeper.basic.BookViewModel
import app.tinks.readkeeper.basic.model.BookFactory
import app.tinks.readkeeper.basic.model.Record
import app.tinks.readkeeper.uicomponent.R
import app.tinks.readkeeper.uicomponent.cellview.BookNote
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun NoteListPage(
    bookViewModel: BookViewModel = viewModel(),
    uuid: String?,
    navController: NavController,
) {
    if (uuid.isNullOrEmpty()) return

    val records by bookViewModel.getRecords(uuid).collectAsState(initial = emptyList())
    val book by bookViewModel.getBook(uuid).collectAsState(initial = BookFactory.buildEmptyBook())
    val noteRecords = records.filterNot { it.note.isNullOrEmpty() }
    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var selectedRecord: Record? by remember { mutableStateOf(null) }

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
    }) { padding ->
        ModalBottomSheetLayout(
            sheetState = modalBottomSheetState,
            scrimColor = Color.Black.copy(alpha = 0.7f),
            sheetContent = {
                EditProgressContent(book = book,
                    lastPage = records.firstOrNull()?.endPage ?: 0,
                    record = selectedRecord,
                    onCancelClicked = {
                        coroutineScope.launch { modalBottomSheetState.hide() }
                    },
                    onDeleteClicked = {
                        bookViewModel.delete(selectedRecord)
                        coroutineScope.launch { modalBottomSheetState.hide() }
                    },
                    onSaveClicked = {
                        bookViewModel.updateRecord(it)
                        coroutineScope.launch { modalBottomSheetState.hide() }
                    })
            }) {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(noteRecords) { record ->
                    BookNote(
                        record = record,
                        pageFormat = book.pageFormat,
                        pages = book.realPages,
                        bigSize = true,
                        modifier = Modifier.clickable {
                            selectedRecord = record
                            coroutineScope.launch { modalBottomSheetState.show() }
                        }
                    )
                }
            }
        }
    }
}
