@file:OptIn(ExperimentalMaterial3Api::class)

package com.github.tinkzhang.homepage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.github.tinkzhang.basic.SCREEN_ROUTE
import com.github.tinkzhang.basic.UserRepository
import com.github.tinkzhang.basic.model.NYBookType
import com.github.tinkzhang.basic.model.WishBookFactory
import com.github.tinkzhang.homepage.quote.QuoteCard
import com.github.tinkzhang.homepage.weeklybook.WeeklyBookViewModel
import com.github.tinkzhang.homepage.weeklybook.ui.WeeklyBookCard
import com.github.tinkzhang.uicomponent.DpBottomPadding
import com.github.tinkzhang.uicomponent.DpVipSectionPadding
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@Destination(start = true)
@Composable
fun Homepage(
    id: Int,
    navController: NavController,
    viewModel: WeeklyBookViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        FilledTonalButton(
            onClick = { navController.navigate(SCREEN_ROUTE.SEARCH) },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search"
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text("Search by Google Books™")
            }
        }
        Spacer(modifier = Modifier.height(DpVipSectionPadding))
        QuoteCard()
        if (viewModel.fictionBooks.collectAsState().value.isNotEmpty()){
            WeeklyBookCard(NYBookType.Fictions, navController)
        }
        if (viewModel.nonFictionBooks.collectAsState().value.isNotEmpty()) {
            WeeklyBookCard(NYBookType.NonFictions, navController)
        }
        Button(onClick = {
            UserRepository.addBook(WishBookFactory.buildSample())
        }) {
            Text("Add Book")
        }

        Button(onClick = {
        }) {
            Text("Get Book")
        }
        Spacer(modifier = Modifier.height(DpBottomPadding))
    }

}
