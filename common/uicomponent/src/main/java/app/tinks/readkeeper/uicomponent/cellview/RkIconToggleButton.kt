package app.tinks.readkeeper.uicomponent.cellview

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import app.tinks.readkeeper.uicomponent.R

/**
 * An [IconToggleButton] with on and off state, and use different icons
 * for different states
 */
@OptIn(ExperimentalAnimationApi::class)
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
        AnimatedContent(targetState = checked, transitionSpec = {
            fadeIn(animationSpec = tween(320, delayMillis = 90)) +
                    scaleIn(initialScale = 1.5f, animationSpec = tween(320, delayMillis = 90)) with
                    fadeOut(animationSpec = tween(90))
        }, label = "") { checked ->
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
        uncheckedIcon = Icons.Filled.BookmarkAdd,
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
