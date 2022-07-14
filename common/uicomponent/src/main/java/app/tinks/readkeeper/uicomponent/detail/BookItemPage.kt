package app.tinks.readkeeper.uicomponent.detail

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import app.tinks.readkeeper.basic.BookViewModel
import app.tinks.readkeeper.basic.model.BookFactory
import app.tinks.readkeeper.basic.model.Status
import app.tinks.readkeeper.firebaseRemoteConfig.FirebaseRemoteConfigWrapper
import app.tinks.readkeeper.uicomponent.*
import app.tinks.readkeeper.uicomponent.R
import app.tinks.readkeeper.uicomponent.cellview.GoogleAdView
import app.tinks.readkeeper.uicomponent.detail.actionsection.ActionSection
import com.google.android.gms.ads.AdSize

@OptIn(ExperimentalMaterial3Api::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun BookItemPage(
    uuid: String,
    openAddProgressDialog: Boolean = false,
    openEditDialog: Boolean = false,
    bookViewModel: BookViewModel,
    navController: NavController
) {
    val book by bookViewModel.getBook(uuid).collectAsState(initial = BookFactory.buildEmptyBook())
    val records by bookViewModel.getRecords(uuid).collectAsState(initial = emptyList())
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = remember(decayAnimationSpec) {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(decayAnimationSpec)
    }
    var showAddProgressDialog by remember { mutableStateOf(openAddProgressDialog) }
    var showEditBookPageDialog by remember { mutableStateOf(openEditDialog) }
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
                showEditBookPageDialog = true
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
        if (book.platform != null) {
            ExtendedFloatingActionButton(text = {
                Text(
                    "Progress", color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }, onClick = {
                showAddProgressDialog = true
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

    }) { paddingValue ->
        val context = LocalContext.current
        val mCustomTabClient = RkCustomTabClient(context)
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
            ActionSection(book = book,
                modifier = Modifier.fillMaxWidth(),
                onAddToWishClick = { bookViewModel.add(book.copy(status = Status.WISH)) },
                onAddToReadingClick = { bookViewModel.add(book.copy(status = Status.READING)) },
                onMoveToReadingClick = { bookViewModel.move(book = book, status = Status.READING) },
                onEditBookClick = { showEditBookPageDialog = true },
                onAddProgressClick = { showAddProgressDialog = true })
            ProgressSection(
                lastRecord = records.lastOrNull(),
                pageFormat = book.pageFormat,
                totalPages = book.basicInfo.pages,
                platform = book.platform
            )
            NoteSection(records.reversed(),
                pageFormat = book.pageFormat,
                realPages = book.realPages,
                onShowAllNotesClick = {
                    //TODO: open all notes list page
                })
            DescriptionSection(description = book.basicInfo.description)
            if ((FirebaseRemoteConfigWrapper.isDetailPagBannerEnabled)) {
                GoogleAdView(
                    adSize = AdSize.MEDIUM_RECTANGLE,
                    keyword = book.basicInfo.title
                )
            }
            if ((FirebaseRemoteConfigWrapper.isDetailPageSearchLinkEnabled
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
            Spacer(modifier = Modifier.padding(DpBottomPadding))
        }
    }

    if (showEditBookPageDialog) {
        Dialog(
            onDismissRequest = { showEditBookPageDialog = false }, properties = DialogProperties(
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = true,
                dismissOnBackPress = true,
            )
        ) {
            EditBookDialogContent(book = book,
                onCancelClicked = { showEditBookPageDialog = false },
                onSaveClicked = {
                    bookViewModel.add(it)
                    showEditBookPageDialog = false
                })
        }
    }

    if (showAddProgressDialog) {
        // TODO: Replace with M3 Full Screen Dialog when available
        Dialog(
            onDismissRequest = { showAddProgressDialog = false }, properties = DialogProperties(
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = true,
                dismissOnBackPress = true,
            )
        ) {
            AddProgressDialogContent(book = book,
                lastPage = records.firstOrNull()?.endPage ?: 0,
                onCancelClicked = {
                    showAddProgressDialog = false
                },
                onSaveClicked = {
                    bookViewModel.add(it)
                    showAddProgressDialog = false
                })
        }
    }
}