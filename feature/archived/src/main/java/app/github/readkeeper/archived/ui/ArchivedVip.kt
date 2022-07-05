package app.github.readkeeper.archived.ui

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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.github.readkeeper.archived.ArchivedViewModel
import app.github.readkeeper.archived.ui.components.ArchivedVipInfoSection
import app.github.tinkzhang.reading.ui.uicomponents.ReadingVipNoteSection
import app.github.tinkzhang.reading.ui.uicomponents.ReadingVipProgressSection
import app.github.tinkzhang.uicomponent.DpBottomPadding
import com.github.readkeeper.archived.R

@OptIn(ExperimentalMaterial3Api::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun ArchivedVip(
    uuid: String,
    archivedViewModel: ArchivedViewModel,
    navController: NavController
) {
    var book by remember {
        mutableStateOf(archivedViewModel.getBook(uuid))
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
                            contentDescription = stringResource(id = R.string.back),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
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
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            ArchivedVipInfoSection(book = book)
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            ReadingVipProgressSection(
                lastRecord = book.records.lastOrNull(),
                pageFormat = book.pageFormat,
                totalPages = book.bookInfo.pages,
                platform = book.platform
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            ReadingVipNoteSection(book.records.reversed(), book.pageFormat, book.bookInfo.pages)
            Spacer(modifier = Modifier.padding(DpBottomPadding))
        }

    }
}
