package com.github.tinkzhang.readkeeper.reading

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DataSaverOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.tinkzhang.readkeeper.R
import com.github.tinkzhang.readkeeper.reading.uicomponents.RkBookInfoSection
import com.github.tinkzhang.readkeeper.reading.uicomponents.RkBookNoteSection
import com.github.tinkzhang.readkeeper.reading.uicomponents.RkBookProgressSection as RkBookProgressSection1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingItemScreen(
    uuid: String,
    readingViewModel: ReadingViewModel,
    navController: NavController
) {
    val book = readingViewModel.getBook(uuid)
    Scaffold(topBar = {
        LargeTopAppBar(title = { Text(book.bookInfo.title) }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        })
    }, floatingActionButton = {
        ExtendedFloatingActionButton(
            text = { Text("Progress", color = MaterialTheme.colorScheme.onPrimaryContainer) },
            onClick = { /*TODO*/ },
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
        ) {
            RkBookInfoSection(book = book)
            Spacer(modifier = Modifier.padding(vertical = 16.dp))
            RkBookProgressSection1(book.records, book.pageFormat, book.platform)
            Spacer(modifier = Modifier.padding(vertical = 16.dp))
            RkBookNoteSection(book.notes, book.pageFormat, book.bookInfo.pages)
        }
    }
}

