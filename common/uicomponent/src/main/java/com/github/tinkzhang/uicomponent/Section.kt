package com.github.tinkzhang.uicomponent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Composable
fun Section(
    header: @Composable () -> Unit = {},
    title: String? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(Modifier.fillMaxWidth(), elevation = DpCardElevation) {
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            Surface(color = MaterialTheme.colorScheme.surfaceVariant) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(DpCardInnerPadding)
                ) {
                    header()
                    if (!title.isNullOrBlank()) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.W500
                            ),
                        )
                    }
                    Divider(
                        Modifier.padding(vertical = DpContentMediumPadding),
                        thickness = DpDividerMedium
                    )
                    content()
                }
            }
        }
    }
}
