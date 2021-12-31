package com.github.tinkzhang.readkeeper.reading.uicomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun BookListCard(
    left: @Composable () -> Unit,
    right: @Composable () -> Unit,
    bottom: @Composable () -> Unit,
    onCardClicked: () -> Unit = {},
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
    ) {
        Surface(color = MaterialTheme.colorScheme.surfaceVariant) {
            Column(Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.width(8.dp))
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable { onCardClicked() },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    left()
                    right()
                }
                Spacer(modifier = Modifier.width(8.dp))
                Divider()
                Spacer(modifier = Modifier.width(8.dp))
                bottom()
            }
        }
    }
}