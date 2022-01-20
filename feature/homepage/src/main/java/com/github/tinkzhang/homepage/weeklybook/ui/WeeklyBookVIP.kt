package com.github.tinkzhang.homepage.weeklybook.ui

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.github.tinkzhang.basic.model.NYBookType
import com.github.tinkzhang.homepage.weeklybook.WeeklyBookViewModel

@OptIn(ExperimentalMaterial3Api::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun WeeklyBookVIP(
    title: String,
    type: NYBookType,
    viewModel: WeeklyBookViewModel = viewModel(),
    navController: NavController
) {
    val book by remember {
        mutableStateOf(viewModel.getBook(title, type))
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
//            ReadingVipInfoSection(book = book)
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
