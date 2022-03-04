package com.github.tinkzhang.readkeeper.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.tinkzhang.readkeeper.model.R

/**
 * An [IconToggleButton] with on and off state, and use different icons
 * for different states
 */
@Composable
fun RkIconToggleButton(
    checkedIcon: ImageVector,
    checkedIconDescription: String,
    uncheckedIcon: ImageVector,
    uncheckedIconDescription: String,
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
) {
    IconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
    ) {
        if (checked) {
            Icon(
                checkedIcon,
                contentDescription = checkedIconDescription,
                tint = MaterialTheme.colorScheme.tertiary
            )
        } else {
            Icon(
                uncheckedIcon,
                contentDescription = uncheckedIconDescription,
                tint = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
fun ReadingIconToggleButton(
    modifier: Modifier = Modifier,
    checked: Boolean = true,
    onCheckedChange: (Boolean) -> Unit,
) {
    RkIconToggleButton(
        checkedIcon = Icons.Filled.BookmarkAdded,
        checkedIconDescription = stringResource(id = R.string.reading_added),
        uncheckedIcon = Icons.Outlined.BookmarkBorder,
        uncheckedIconDescription = stringResource(id = R.string.add_reading),
        checked = checked,
        modifier = modifier,
        onCheckedChange = onCheckedChange,
    )
}

@Composable
fun WishIconToggleButton(
    modifier: Modifier = Modifier,
    checked: Boolean = true,
    onCheckedChange: (Boolean) -> Unit,
) {
    RkIconToggleButton(
        checkedIcon = Icons.Filled.Favorite,
        checkedIconDescription = stringResource(id = R.string.wished),
        uncheckedIcon = Icons.Outlined.FavoriteBorder,
        uncheckedIconDescription = stringResource(id = R.string.add_wish),
        checked = checked,
        modifier = modifier,
        onCheckedChange = onCheckedChange,
    )
}

@Preview
@Composable
fun ReadingIconToggleButtonCheckedPreview() {
    var checked by remember { mutableStateOf(true) }
    ReadingIconToggleButton(checked = checked) {
        checked = it
    }
}

@Preview
@Composable
fun WishIconToggleButtonUncheckedPreview() {
    var checked by remember {
        mutableStateOf(false)
    }
    WishIconToggleButton(checked = checked) {
        checked = it
    }
}
