package com.github.tinkzhang.readkeeper.reading

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DataSaverOn
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.github.tinkzhang.readkeeper.R
import com.github.tinkzhang.readkeeper.reading.uicomponents.*

@OptIn(ExperimentalMaterial3Api::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun ReadingItemScreen(
    uuid: String,
    readingViewModel: ReadingViewModel,
    navController: NavController
) {
    val book = readingViewModel.getBook(uuid)
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = remember(decayAnimationSpec) {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(decayAnimationSpec)
    }
    var showAddProgressDialog by remember { mutableStateOf(false) }
    var showEditBookPageDialog by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text(book.bookInfo.title) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        showEditBookPageDialog = true
                    }) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }, floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Progress", color = MaterialTheme.colorScheme.onPrimaryContainer) },
                onClick = {
                    if (book.records.isEmpty()) {
                        showEditBookPageDialog = true
                    } else {
                        showAddProgressDialog = true
                    }
                },

                icon = {
                    Icon(
                        Icons.Default.DataSaverOn,
                        contentDescription = "Add Reading Progress",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                })
        }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            RkBookInfoSection(book = book)
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            RkBookProgressSection(
                lastRecord = book.records.lastOrNull(),
                pageFormat = book.pageFormat,
                totalPages = book.bookInfo.pages,
                platform = book.platform
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            RkBookNoteSection(book.records, book.pageFormat, book.bookInfo.pages)
            Spacer(modifier = Modifier.padding(vertical = 48.dp))
        }

        if (showEditBookPageDialog) {
            Dialog(
                onDismissRequest = { showEditBookPageDialog = false },
                properties = DialogProperties(
                    dismissOnClickOutside = false,
                    usePlatformDefaultWidth = true,
                    dismissOnBackPress = true,
                )
            ) {
                RkEditBookPageContent(
                    onCancelClicked = { showEditBookPageDialog = false },
                    onSaveClicked = { showEditBookPageDialog })
            }
        }

        if (showAddProgressDialog) {
            // TODO: Replace with M3 Full Screen Dialog when available
            Dialog(
                onDismissRequest = { showAddProgressDialog = false },
                properties = DialogProperties(
                    dismissOnClickOutside = false,
                    usePlatformDefaultWidth = true,
                    dismissOnBackPress = true,
                )
            ) {
                RkProgressDialogContent()
            }
        }
    }
}
