package com.github.tinkzhang.homepage.weeklybook.ui

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.github.tinkzhang.firebaseRemoteConfig.FirebaseRemoteConfigWrapper
import com.github.tinkzhang.homepage.weeklybook.WeeklyBookViewModel
import com.github.tinkzhang.uicomponent.*
import com.github.tinkzhang.wish.ui.components.VipSearchEngineSection
import com.google.android.gms.ads.AdSize
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun WeeklyBookVIP(
    title: String,
    viewModel: WeeklyBookViewModel = viewModel(),
    navController: NavController
) {
    val book by remember {
        mutableStateOf(viewModel.getBook(title))
    }
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = remember(decayAnimationSpec) {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(decayAnimationSpec)
    }
    val scope = rememberCoroutineScope()
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
                .padding(horizontal = DpContentLargePadding)
                .verticalScroll(rememberScrollState())
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                BookCardImageLarge(url = book.bookImage, title = book.title)
                Spacer(modifier = Modifier.width(DpContentLargePadding))
                WeeklyVipMetadata(book = book)
            }
            Spacer(modifier = Modifier.height(DpVipSectionPadding))
            DescriptionSection(description = book.description)
            FilledTonalButton(
                onClick = {
                    scope.launch {
                        val result = scaffoldState.snackbarHostState.showSnackbar(
                            "Added into Wish List",
                            "Undo"
                        )
                        when (result) {
                            SnackbarResult.Dismissed -> {}
                            SnackbarResult.ActionPerformed -> viewModel.addToWish(book)
                        }
                    }
                },
                Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(DpContentMediumPadding))
                androidx.compose.material3.Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = null
                )
                androidx.compose.material3.Icon(
                    Icons.Default.Favorite,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(DpContentMediumPadding))
                Text("Add to Wish List")
                Spacer(modifier = Modifier.height(DpContentMediumPadding))
            }
            Spacer(modifier = Modifier.height(DpVipSectionPadding))
            AmazonLinkSection(book.amazonProductUrl, mCustomTabClient)
            GoogleAdView(
                adSize = AdSize.MEDIUM_RECTANGLE,
                keyword = book.title
            )
            if (FirebaseRemoteConfigWrapper.isWishVipSearchLinkEnabled
                && !FirebaseRemoteConfigWrapper.searchEngines?.searchEngines.isNullOrEmpty()
            ) {
                VipSearchEngineSection(
                    book.title,
                    FirebaseRemoteConfigWrapper.searchEngines!!.searchEngines,
                    mCustomTabClient,
                )
            }
        }
    }
}
