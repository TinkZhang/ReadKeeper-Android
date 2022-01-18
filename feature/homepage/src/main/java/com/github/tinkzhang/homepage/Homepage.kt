package com.github.tinkzhang.homepage

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.tinkzhang.basic.SCREEN_ROUTE
import com.github.tinkzhang.basic.UserRepository
import com.github.tinkzhang.basic.model.WishBookFactory
import com.github.tinkzhang.homepage.quote.QuoteCard

@Composable
fun Homepage(
    navController: NavController,
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        FilledTonalButton(onClick = { navController.navigate(SCREEN_ROUTE.SEARCH)}, modifier = Modifier.padding(16.dp).fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search"
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text("Search by Google Books™")
            }
        }

        QuoteCard()

        Button(onClick = {
            UserRepository.addBook(WishBookFactory.buildSample())
        }) {
            Text("Add Book")
        }

        Button(onClick = {
        }) {
            Text("Get Book")
        }
    }

}
