package app.tinks.readkeeper.homepage.weeklybook.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.SnackbarHost
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import app.tinks.readkeeper.basic.model.NYTimesBook
import app.tinks.readkeeper.basic.model.Status
import app.tinks.readkeeper.firebaseRemoteConfig.FirebaseRemoteConfigWrapper
import app.tinks.readkeeper.homepage.weeklybook.WeeklyBookViewModel
import app.tinks.readkeeper.uicomponent.DpBottomPadding
import app.tinks.readkeeper.uicomponent.DpContentLargePadding
import app.tinks.readkeeper.uicomponent.RkCustomTabClient
import app.tinks.readkeeper.uicomponent.cellview.BookCardImageLarge
import app.tinks.readkeeper.uicomponent.detail.GetBookSection
import app.tinks.readkeeper.uicomponent.detail.actionsection.SearchActionSection

@OptIn(ExperimentalMaterial3Api::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun WeeklyBookVIP(
    viewModel: WeeklyBookViewModel = viewModel(),
    navController: NavController
) {
    val book by viewModel.book.collectAsState(initial = NYTimesBook())
    val topBarScrollState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        state = topBarScrollState
    )
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = scaffoldState.snackbarHostState) },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text(book.title) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) {
        val context = LocalContext.current
        val mCustomTabClient = RkCustomTabClient(context)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                BookCardImageLarge(url = book.bookImage, title = book.title)
                Spacer(modifier = Modifier.width(DpContentLargePadding))
                WeeklyVipMetadata(book = book, modifier = Modifier.alpha(0.8f))
            }
            SearchActionSection(
                modifier = Modifier.fillMaxWidth(),
                onAddToReadingClick = { viewModel.addTo(book, Status.READING) },
                onAddToWishClick = { viewModel.addTo(book, Status.WISH) }
            )
            DescriptionSection(description = book.description)
            if ((FirebaseRemoteConfigWrapper.isDetailPageSearchLinkEnabled
                        && !FirebaseRemoteConfigWrapper.searchEngines?.searchEngines.isNullOrEmpty())
                || !book.amazonProductUrl.isNullOrEmpty()
            ) {
                GetBookSection(
                    title = book.title,
                    searchEngines = if (FirebaseRemoteConfigWrapper.isDetailPageSearchLinkEnabled) {
                        FirebaseRemoteConfigWrapper.searchEngines?.searchEngines
                    } else null,
                    client = mCustomTabClient,
                    modifier = Modifier.fillMaxWidth(),
                    amazonLink = book.amazonProductUrl
                )
            }
            Spacer(modifier = Modifier.padding(DpBottomPadding))
        }
    }
}
