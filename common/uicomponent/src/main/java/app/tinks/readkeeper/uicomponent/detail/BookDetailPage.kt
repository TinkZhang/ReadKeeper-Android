package app.tinks.readkeeper.uicomponent.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DataSaverOn
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.tinks.readkeeper.basic.BookViewModel
import app.tinks.readkeeper.basic.model.BookFactory
import app.tinks.readkeeper.basic.model.Status
import app.tinks.readkeeper.firebaseRemoteConfig.FirebaseRemoteConfigWrapper
import app.tinks.readkeeper.uicomponent.DpBottomPadding
import app.tinks.readkeeper.uicomponent.R
import app.tinks.readkeeper.uicomponent.RkCustomTabClient
import app.tinks.readkeeper.uicomponent.cellview.GoogleAdView
import app.tinks.readkeeper.uicomponent.detail.actionsection.ActionSection
import com.google.android.gms.ads.AdSize
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class
)
@Composable
fun BookDetailPage(
    uuid: String,
    openAddProgressDialog: Boolean = false,
    openEditDialog: Boolean = false,
    bookViewModel: BookViewModel,
    navController: NavController
) {
    val book by bookViewModel.getBook(uuid).collectAsState(initial = BookFactory.buildEmptyBook())
    val records by bookViewModel.getRecords(uuid).collectAsState(initial = emptyList())
    val topBarScrollState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        state = topBarScrollState
    )

    var showDeleteBookDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(
        if (openAddProgressDialog) ModalBottomSheetValue.Expanded
        else ModalBottomSheetValue.Hidden
    )

    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        LargeTopAppBar(title = { Text(book.basicInfo.title) }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }, actions = {
            IconButton(onClick = {
                navController.navigate("edit_book/${book.basicInfo.uuid}")
            }) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }, scrollBehavior = scrollBehavior
        )
    }, floatingActionButton = {
        if (modalBottomSheetState.isVisible) return@Scaffold
        if (book.platform != null) {
            ExtendedFloatingActionButton(text = {
                Text(
                    "Progress", color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }, onClick = {
                coroutineScope.launch { modalBottomSheetState.show() }
            }, icon = {
                Icon(
                    Icons.Default.DataSaverOn,
                    contentDescription = "Add Reading Progress",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            })
        } else {
            ExtendedFloatingActionButton(text = {
                Text(
                    "Edit", color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }, onClick = {
                navController.navigate("edit_book/${book.basicInfo.uuid}")
            }, icon = {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Edit Book",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            })
        }

    }) { paddingValue ->
        val context = LocalContext.current
        val mCustomTabClient = RkCustomTabClient(context)

        ModalBottomSheetLayout(sheetState = modalBottomSheetState,
            scrimColor = Color.Black.copy(alpha = 0.7f),
            sheetContent = {
                AddProgressContent(book = book,
                    lastPage = records.lastOrNull()?.endPage ?: 0,
                    onCancelClicked = {
                        coroutineScope.launch { modalBottomSheetState.hide() }
                    },
                    onSaveClicked = {
                        bookViewModel.add(it)
                        bookViewModel.update(book.copy(progress = it.endPage))
                        coroutineScope.launch {
                            modalBottomSheetState.hide()
                        }
                    })
            }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValue)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                InfoSection(book = book)
                ActionSection(
                    book = book,
                    modifier = Modifier.fillMaxWidth(),
                    onAddToWishClick = { bookViewModel.add(book.copy(status = Status.WISH)) },
                    onAddToReadingClick = { bookViewModel.add(book.copy(status = Status.READING)) },
                    onMoveToReadingClick = {
                        bookViewModel.move(
                            book = book, status = Status.READING
                        )
                    },
                )
                ProgressSection(
                    lastRecord = records.lastOrNull(),
                    pageFormat = book.pageFormat,
                    totalPages = book.realPages,
                    platform = book.platform
                )
                NoteSection(records.reversed().filterNot { it.note.isNullOrEmpty() },
                    pageFormat = book.pageFormat,
                    realPages = book.realPages,
                    onShowAllNotesClick = {
                        navController.navigate("all_notes/$uuid")
                    })
                DescriptionSection(description = book.basicInfo.description)
                if (FirebaseRemoteConfigWrapper.isDetailPagBannerEnabled) {
                    if (book.status == Status.WISH || records.size in 3..10) {
                        GoogleAdView(
                            adSize = AdSize.FLUID, keyword = book.basicInfo.title
                        )
                    } else if (records.size > 3) {
                        GoogleAdView(
                            adSize = AdSize.MEDIUM_RECTANGLE, keyword = book.basicInfo.title
                        )
                    }
                }
                if ((book.status == Status.WISH
                            && FirebaseRemoteConfigWrapper.isDetailPageSearchLinkEnabled
                            && !FirebaseRemoteConfigWrapper.searchEngines?.searchEngines.isNullOrEmpty())
                    || !book.basicInfo.amazonLink.isNullOrEmpty()
                ) {
                    GetBookSection(
                        title = book.basicInfo.title,
                        searchEngines = if (FirebaseRemoteConfigWrapper.isDetailPageSearchLinkEnabled) {
                            FirebaseRemoteConfigWrapper.searchEngines?.searchEngines
                        } else null,
                        client = mCustomTabClient,
                        modifier = Modifier.fillMaxWidth(),
                        amazonLink = book.basicInfo.amazonLink
                    )
                }
                if (book.status == Status.READING || book.status == Status.WISH) {
                    OutlinedButton(
                        onClick = { bookViewModel.move(book, Status.ARCHIVED) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = stringResource(id = R.string.add_achived))
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            Icons.Filled.Archive,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
                if (book.status == Status.READING || book.status == Status.WISH || book.status == Status.ARCHIVED) {
                    OutlinedButton(
                        onClick = { showDeleteBookDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.delete_book),
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(DpBottomPadding))
            }
        }

        if (showDeleteBookDialog) {
            AlertDialog(onDismissRequest = { showDeleteBookDialog = false }, confirmButton = {
                FilledTonalButton(onClick = {
                    bookViewModel.delete(book)
                    showDeleteBookDialog = false
                }) {
                    Text(stringResource(id = R.string.confirm))
                }
            }, dismissButton = {
                TextButton(onClick = { showDeleteBookDialog = false }) {
                    Text(stringResource(id = R.string.cancel))
                }
            }, icon = {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = stringResource(id = R.string.delete),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }, text = { Text(stringResource(id = R.string.delete_book_confirmation)) })
        }
    }
}