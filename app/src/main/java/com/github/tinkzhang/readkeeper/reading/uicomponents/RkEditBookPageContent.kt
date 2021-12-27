package com.github.tinkzhang.readkeeper.reading.uicomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.tinkzhang.readkeeper.common.data.PageFormat
import com.github.tinkzhang.readkeeper.common.data.ReadingPlatform

@Composable
fun RkEditBookPageContent(
    onCancelClicked: () -> Unit = {},
    onSaveClicked: () -> Unit = {},
) {
    Surface(color = MaterialTheme.colorScheme.surface) {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(8.dp)
        ) {
            BookPageFormatSection()
            Divider()
            PlatformSelectionSection()
            Row(Modifier.align(Alignment.End)) {
                TextButton(onClick = onCancelClicked) {
                    Text("Cancel")
                }
                TextButton(onClick = onSaveClicked) {
                    Text("Save")
                }
            }
        }
    }
}

@Composable
fun PlatformSelectionSection() {
    var state by remember { mutableStateOf(ReadingPlatform.GENERAL) }
    Column(Modifier.fillMaxWidth()) {
        Text("Read Platform", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(4.dp))
        ReadingPlatform.values().forEach {
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable { state = it }, verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = state.name == it.name,
                    onClick = { state = it },
                    modifier = Modifier.padding(4.dp)
                )
                Icon(
                    painterResource(id = it.icon),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Spacer(Modifier.width(8.dp))
                Text(it.label, style = MaterialTheme.typography.titleMedium)
            }

        }
    }
}

@Composable
fun BookPageFormatSection() {
    var pageFormatState by remember { mutableStateOf(PageFormat.PAGE) }
    var pageState by remember { mutableStateOf("100") }
    var percentageState by remember { mutableStateOf(PageFormat.PERCENT_100) }
    Column(Modifier.fillMaxWidth()) {
        Text("Page Format", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(4.dp))
        if (pageFormatState == PageFormat.PAGE) {
            Column() {
                Row(Modifier.fillMaxWidth()) {
                    FilledTonalButton(
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 0.dp,
                            bottomEnd = 0.dp,
                            bottomStart = 8.dp
                        ),
                        modifier = Modifier.weight(1.0f)
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                        Text("Pages")
                    }
                    OutlinedButton(
                        onClick = { pageFormatState = PageFormat.PERCENT_100 },
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 8.dp,
                            bottomEnd = 8.dp,
                            bottomStart = 0.dp
                        ),
                        modifier = Modifier.weight(1.0f)
                    ) {
                        Text("Percent %")
                    }
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    "Pages:",
                    Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.height(8.dp))
                TextField(
                    value = pageState,
                    onValueChange = { value -> pageState = value },
                    Modifier.align(Alignment.CenterHorizontally),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    shape = androidx.compose.material.MaterialTheme.shapes.large,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
                )
                Spacer(Modifier.height(16.dp))
            }

        } else {
            Column() {
                Row(Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = { pageFormatState = PageFormat.PAGE },
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 0.dp,
                            bottomEnd = 0.dp,
                            bottomStart = 8.dp
                        ),
                        modifier = Modifier.weight(1.0f)
                    ) {
                        Text("Pages")
                    }
                    FilledTonalButton(
                        onClick = { pageFormatState = PageFormat.PERCENT_100 },
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 8.dp,
                            bottomEnd = 8.dp,
                            bottomStart = 0.dp
                        ),
                        modifier = Modifier.weight(1.0f)
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                        Text("Percent %")
                    }
                }
                Text("Percent Format", style = MaterialTheme.typography.titleMedium)

                PageFormat.values().filterNot { it == PageFormat.PAGE }.forEach {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable { percentageState = it },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = percentageState == it,
                            onClick = { percentageState = it })
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = when (it) {
                                PageFormat.PAGE -> ""
                                PageFormat.PERCENT_100 -> "100%  e.g. 12%"
                                PageFormat.PERCENT_1000 -> "100.0%  e.g. 12.3%"
                                PageFormat.PERCENT_10000 -> "100.00%  e.g. 12.34%"
                            }, style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun RkEditBookPageContentPreview() {
    RkEditBookPageContent()
}