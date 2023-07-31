package app.tinks.readkeeper.uicomponent.editbook

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.basic.model.PageFormat
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.R
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPageField(
    pages: Int,
    modifier: Modifier = Modifier,
    pageFormat: PageFormat = PageFormat.PAGE,
    onPageFormatChange: (PageFormat) -> Unit = {},
    onPageNumberChange: (String) -> Unit = {},
) {
    Column(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(2.dp)
            )
            .padding(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        var newPageFormat by remember(pageFormat) { mutableStateOf(pageFormat) }
        var newPages by remember(pages) { mutableStateOf(pages.toString()) }
        Text(text = stringResource(id = R.string.page_format))
        if (newPageFormat == PageFormat.PAGE) {
            Row(
                Modifier.fillMaxWidth()
            ) {
                FilledTonalButton(
                    onClick = {
                        newPageFormat = PageFormat.PAGE
                        onPageFormatChange(PageFormat.PAGE)
                    }, shape = RoundedCornerShape(
                        topStart = 8.dp, topEnd = 0.dp, bottomEnd = 0.dp, bottomStart = 8.dp
                    ), modifier = Modifier.weight(1.0f)
                ) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "Check",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Text(stringResource(id = R.string.pages))
                }
                OutlinedButton(
                    onClick = {
                        newPageFormat = PageFormat.PERCENT_100
                        onPageFormatChange(PageFormat.PERCENT_100)
                    }, shape = RoundedCornerShape(
                        topStart = 0.dp, topEnd = 8.dp, bottomEnd = 8.dp, bottomStart = 0.dp
                    ), modifier = Modifier.weight(1.0f)
                ) {
                    Text(stringResource(id = R.string.percent))
                }
            }
        } else {
            Row(Modifier.fillMaxWidth()) {
                OutlinedButton(
                    onClick = {
                        newPageFormat = PageFormat.PAGE
                        onPageFormatChange(PageFormat.PAGE)
                    }, shape = RoundedCornerShape(
                        topStart = 8.dp, topEnd = 0.dp, bottomEnd = 0.dp, bottomStart = 8.dp
                    ), modifier = Modifier.weight(1.0f)
                ) {
                    Text(stringResource(id = R.string.pages))
                }
                FilledTonalButton(
                    onClick = { onPageFormatChange(PageFormat.PERCENT_100) },
                    shape = RoundedCornerShape(
                        topStart = 0.dp, topEnd = 8.dp, bottomEnd = 8.dp, bottomStart = 0.dp
                    ),
                    modifier = Modifier.weight(1.0f)
                ) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Text(stringResource(id = R.string.percent))
                }
            }
        }
        Crossfade(
            targetState = newPageFormat, modifier = Modifier.fillMaxWidth(),
            label = ""
        ) { format ->
            when (format) {
                PageFormat.PAGE -> {
                    OutlinedTextField(
                        value = newPages,
                        onValueChange = { value: String ->
                            newPages = value
                            onPageNumberChange(value)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Center),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        isError = newPages.toIntOrNull() == null
                    )
                }

                else -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        PageFormat.values().filterNot { it == PageFormat.PAGE }.forEach {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        newPageFormat = it
                                        onPageFormatChange(it)
                                    }, verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(selected = newPageFormat == it, onClick = {
                                    newPageFormat = it
                                    onPageFormatChange(it)
                                })
                                Text(
                                    text = when (it) {
                                        PageFormat.PAGE -> ""
                                        PageFormat.PERCENT_100 -> stringResource(id = R.string.percent_100)
                                        PageFormat.PERCENT_1000 -> stringResource(id = R.string.percent_1000)
                                        PageFormat.PERCENT_10000 -> stringResource(id = R.string.percent_10000)
                                    }, style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}

@PreviewAnnotation
@Composable
private fun EditPageFieldPage() {
    ReadKeeperTheme {
        Surface {
            EditPageField(pageFormat = PageFormat.PAGE, pages = 100)
        }
    }
}

@PreviewAnnotation
@Composable
private fun EditPageFieldPercent() {
    ReadKeeperTheme {
        Surface {
            EditPageField(pageFormat = PageFormat.PERCENT_1000, pages = 100)
        }
    }
}
