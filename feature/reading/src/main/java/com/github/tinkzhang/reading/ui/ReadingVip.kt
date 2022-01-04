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
import com.github.tinkzhang.reading.R
import com.github.tinkzhang.reading.ReadingViewModel
import com.github.tinkzhang.reading.ui.uicomponents.EditBookDialogContent
import com.github.tinkzhang.reading.ui.uicomponents.ReadingVipInfoSection
import com.github.tinkzhang.reading.ui.uicomponents.ReadingVipNoteSection
import com.github.tinkzhang.reading.ui.uicomponents.ReadingVipProgressSection
import com.github.tinkzhang.uicomponent.AddProgressDialogContent

@OptIn(ExperimentalMaterial3Api::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun ReadingVip(
    uuid: String,
    openAddProgressDialog: Boolean,
    openEditDialog: Boolean,
    readingViewModel: ReadingViewModel,
    navController: NavController
) {
    var book by remember {
        mutableStateOf(readingViewModel.getBook(uuid))
    }
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = remember(decayAnimationSpec) {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(decayAnimationSpec)
    }
    var showAddProgressDialog by remember { mutableStateOf(openAddProgressDialog) }
    var showEditBookPageDialog by remember { mutableStateOf(openEditDialog) }
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
            if (book.formatEdited) {
                ExtendedFloatingActionButton(
                    text = {
                        Text(
                            "Progress",
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    },
                    onClick = {
                        showAddProgressDialog = true
                    },
                    icon = {
                        Icon(
                            Icons.Default.DataSaverOn,
                            contentDescription = "Add Reading Progress",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    })
            } else {
                ExtendedFloatingActionButton(
                    text = { Text("Edit", color = MaterialTheme.colorScheme.onPrimaryContainer) },
                    onClick = {
                        showEditBookPageDialog = true
                    },

                    icon = {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit Book",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    })
            }

        }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            ReadingVipInfoSection(book = book)
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            ReadingVipProgressSection(
                lastRecord = book.records.lastOrNull(),
                pageFormat = book.pageFormat,
                totalPages = book.bookInfo.pages,
                platform = book.platform
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            ReadingVipNoteSection(book.records.reversed(), book.pageFormat, book.bookInfo.pages)
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
                EditBookDialogContent(
                    book = book,
                    onCancelClicked = { showEditBookPageDialog = false },
                    onSaveClicked = {
                        readingViewModel.addBook(it)
                        readingViewModel.updateLocalList(it)
                        book = it
                        showEditBookPageDialog = false
                    })
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
                AddProgressDialogContent(
                    book = book,
                    onCancelClicked = {
                        showAddProgressDialog = false
                    },
                    onSaveClicked = {
                        readingViewModel.addBook(it)
                        readingViewModel.updateLocalList(it)
                        book = it
                        showAddProgressDialog = false
                    }
                )
            }
        }
    }
}
