package com.github.tinkzhang.wish.ui

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.tinkzhang.wish.WishViewModel
import com.github.tinkzhang.wish.ui.components.WishVipInfoSection

@OptIn(ExperimentalMaterial3Api::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun WishVip(
    uuid: String,
    wishViewModel: WishViewModel,
    navController: NavController
) {
    var book by remember {
        mutableStateOf(wishViewModel.getBook(uuid))
    }
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = remember(decayAnimationSpec) {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(decayAnimationSpec)
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text(book.bookInfo.title) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = com.github.tinkzhang.wish.R.string.back),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                    }) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            WishVipInfoSection(book = book)
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            FilledTonalButton(onClick = {wishViewModel.moveToReading(uuid)}, Modifier.fillMaxWidth()) {
                androidx.compose.material3.Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = null
                )
                androidx.compose.material3.Icon(
                    Icons.Default.BookmarkAdd,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Move to Reading List")
            }
            Spacer(modifier = Modifier.padding(vertical = 48.dp))
        }

    }
}
