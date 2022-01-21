package com.github.tinkzhang.homepage.weeklybook.ui

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.github.tinkzhang.homepage.weeklybook.WeeklyBookViewModel
import com.github.tinkzhang.uicomponent.BookCardImageLarge

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
    Scaffold(
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                BookCardImageLarge(url = book.bookImage, title = book.title)
                WeeklyVipMetadata(book = book)
            }
            FilledTonalButton(
                onClick = { viewModel.addToWish(book) },
                Modifier.fillMaxWidth()
            ) {
                androidx.compose.material3.Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = null
                )
                androidx.compose.material3.Icon(
                    Icons.Default.Favorite,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Add to Wish List")
            }
        //            Spacer(modifier = Modifier.padding(vertical = 8.dp))
//            ReadingVipProgressSection(
//                lastRecord = book.records.lastOrNull(),
//                pageFormat = book.pageFormat,
//                totalPages = book.bookInfo.pages,
//                platform = book.platform
//            )
//            Spacer(modifier = Modifier.padding(vertical = 8.dp))
//            ReadingVipNoteSection(book.records.reversed(), book.pageFormat, book.bookInfo.pages)
//            Spacer(modifier = Modifier.padding(vertical = 48.dp))
        }
    }
}
