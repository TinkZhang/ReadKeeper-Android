package app.tinks.readkeeper.homepage.weeklybook.ui

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.SnackbarHost
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
    title: String,
    viewModel: WeeklyBookViewModel,
    navController: NavController
) {
    val book by remember {
        mutableStateOf(viewModel.getBook(title))
    }
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val topBarScrollState = rememberTopAppBarScrollState()
    val scrollBehavior = remember(decayAnimationSpec) {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
            decayAnimationSpec,
            state = topBarScrollState
        )
    }
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
