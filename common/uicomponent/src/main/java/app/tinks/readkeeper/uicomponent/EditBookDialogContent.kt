package app.tinks.readkeeper.uicomponent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.BookFactory
import app.tinks.readkeeper.basic.model.PageFormat
import app.tinks.readkeeper.basic.model.Platform

@ExperimentalMaterial3Api
@Composable
fun EditBookDialogContent(
    book: Book,
    onCancelClicked: () -> Unit = {},
    onSaveClicked: (Book) -> Unit = {},
) {
    var platformState by remember { mutableStateOf(book.platform) }
    var pageFormatState by remember { mutableStateOf(book.pageFormat) }
    var pageState by remember { mutableStateOf(book.basicInfo.pages.toString()) }

    Surface(color = MaterialTheme.colorScheme.surface) {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(8.dp)
        ) {
            BookPageFormatSection(
                pageFormatState = pageFormatState,
                pageState = pageState,
                onPageFormatChanged = { pageFormatState = it },
                onPageChanged = { pageState = it }
            )
            Divider()
            Spacer(modifier = Modifier.height(16.dp))
            PlatformSelectionSection(platformState) { platformState = it }
            Row(Modifier.align(Alignment.End)) {
                TextButton(onClick = onCancelClicked) {
                    Text("Cancel")
                }
                TextButton(onClick = {
                    val pages: Int = when (pageFormatState) {
                        PageFormat.PAGE -> pageState.toInt()
                        PageFormat.PERCENT_100 -> 100
                        PageFormat.PERCENT_1000 -> 1000
                        PageFormat.PERCENT_10000 -> 10000
                    }
                    onSaveClicked(
                        book.update(
                            platform = platformState,
                            realPages = pages,
                            pageFormat = pageFormatState,
                        )
                    )
                }) {
                    Text("Save")
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun PlatformSelectionSection(
    platformState: Platform?,
    onPlatformSelect: (Platform) -> Unit = {}
) {
    Column(Modifier.fillMaxWidth()) {
        Text("Read Platform", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(4.dp))
        Platform.values().forEach {
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable { onPlatformSelect(it) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = if (platformState == null) false else platformState.name == it.name,
                    onClick = { onPlatformSelect(it) },
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

@ExperimentalMaterial3Api
@Composable
private fun BookPageFormatSection(
    pageFormatState: PageFormat,
    pageState: String,
    onPageFormatChanged: (PageFormat) -> Unit = {},
    onPageChanged: (String) -> Unit = {}
) {
    Column(Modifier.fillMaxWidth()) {
        Text("Page Format", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(4.dp))
        if (pageFormatState == PageFormat.PAGE) {
            Column() {
                Row(Modifier.fillMaxWidth()) {
                    FilledTonalButton(
                        onClick = { onPageFormatChanged(PageFormat.PAGE) },
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
                        onClick = { onPageFormatChanged(PageFormat.PERCENT_100) },
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
                OutlinedTextField(
                    value = pageState,
                    onValueChange = { onPageChanged(it) },
                    Modifier.align(Alignment.CenterHorizontally),
                    textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = MaterialTheme.colorScheme.onSurface,
                        containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.04f),
                    )
                )
                Spacer(Modifier.height(16.dp))
            }

        } else {
            Column() {
                Row(Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = { onPageFormatChanged(PageFormat.PAGE) },
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
                        onClick = { onPageFormatChanged(PageFormat.PERCENT_100) },
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
                            .clickable { onPageFormatChanged(it) },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = pageFormatState == it,
                            onClick = { onPageFormatChanged(it) })
                        Text(
                            text = when (it) {
                                PageFormat.PAGE -> ""
                                PageFormat.PERCENT_100 -> "100% e.g. 12%"
                                PageFormat.PERCENT_1000 -> "100.0% e.g. 12.3%"
                                PageFormat.PERCENT_10000 -> "100.00% e.g. 12.34%"
                            }, style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun EditBookPageContentPreview() {
    EditBookDialogContent(
        book = BookFactory.buildReadingSample()
    )
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun EditBookPageContentPercentPreview() {
    EditBookDialogContent(
        book = BookFactory.buildReadingSample().copy(pageFormat = PageFormat.PERCENT_1000)
    )
}