@file:OptIn(ExperimentalMaterial3Api::class)

package app.tinks.readkeeper.homepage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import app.tinks.readkeeper.basic.SCREEN_ROUTE
import app.tinks.readkeeper.basic.model.NYBookType
import app.tinks.readkeeper.homepage.quote.QuoteCard
import app.tinks.readkeeper.homepage.weeklybook.HomepageViewModel
import app.tinks.readkeeper.homepage.weeklybook.ui.HomepageReadingCard
import app.tinks.readkeeper.homepage.weeklybook.ui.NoReadingCard
import app.tinks.readkeeper.homepage.weeklybook.ui.WeeklyBookCard
import app.tinks.readkeeper.uicomponent.DpBottomPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Homepage(
    navController: NavController,
    viewModel: HomepageViewModel = viewModel(),
) {
    val firstReading by viewModel.getFirstReading().collectAsState(initial = null)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        QuoteCard()
        if (firstReading.isNullOrEmpty()) {
            NoReadingCard(onClick = { navController.navigate(SCREEN_ROUTE.SEARCH) })
        } else {
            val book = firstReading?.first()
            HomepageReadingCard(book = book, onAddProgressClick = {
                navController.navigate("reading_item/${book?.basicInfo?.uuid}?open_progress_dialog=${true}")
            }, onClick = {
                navController.navigate("reading_item/${book?.basicInfo?.uuid}")
            })
        }
//        AllHistoryCard(viewModel.getAllRecords().collectAsState(initial = emptyList()).value)
        if (viewModel.fictionBooks.collectAsState().value.isNotEmpty()) {
            WeeklyBookCard(NYBookType.Fictions, onBookClick = {
                navController.navigate("weekly_item/$it")
            })
        }
        if (viewModel.nonFictionBooks.collectAsState().value.isNotEmpty()) {
            WeeklyBookCard(NYBookType.NonFictions, onBookClick = {
                navController.navigate("weekly_item/$it")
            })
        }
        Spacer(modifier = Modifier.height(DpBottomPadding))
    }
}
