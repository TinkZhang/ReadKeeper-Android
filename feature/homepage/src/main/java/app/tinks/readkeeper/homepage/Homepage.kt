@file:OptIn(ExperimentalMaterial3Api::class)

package app.tinks.readkeeper.homepage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.tinks.readkeeper.basic.model.NYBookType
import app.tinks.readkeeper.homepage.quote.QuoteCard
import app.tinks.readkeeper.homepage.weeklybook.WeeklyBookViewModel
import app.tinks.readkeeper.homepage.weeklybook.ui.WeeklyBookCard
import app.tinks.readkeeper.uicomponent.DpBottomPadding
import app.tinks.readkeeper.uicomponent.DpVipSectionPadding

@Composable
fun Homepage(
    navController: NavController,
    viewModel: WeeklyBookViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
//        HomepageSearchBar(onClick = { navController.navigate(SCREEN_ROUTE.SEARCH) })
        Spacer(modifier = Modifier.height(DpVipSectionPadding))
        QuoteCard()
        if (viewModel.fictionBooks.collectAsState().value.isNotEmpty()) {
            WeeklyBookCard(NYBookType.Fictions, navController)
        }
        if (viewModel.nonFictionBooks.collectAsState().value.isNotEmpty()) {
            WeeklyBookCard(NYBookType.NonFictions, navController)
        }
        Spacer(modifier = Modifier.height(DpBottomPadding))
    }
}


