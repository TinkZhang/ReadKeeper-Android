package com.github.tinkzhang.readkeeper.ui.components

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.github.tinkzhang.readkeeper.R
import com.github.tinkzhang.uicomponent.HomepageSearchBar
import com.github.tinkzhang.uicomponent.PreviewAnnotation
import com.github.tinkzhang.uicomponent.RkProfileImage
import com.github.tinkzhang.uicomponent.theme.ReadKeeperTheme

@Composable
fun RkMainTopBar(
    isLogged: Boolean,
    profileUrl: String = "",
    onProfileClickAction: () -> Unit = {},
    onSearchClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_readkeeperlogo),
                contentDescription = "",
            )
        },
        title = {
            HomepageSearchBar(
                onClick = onSearchClick
            )
        },
        actions = {
            if (isLogged) {
                IconButton(
                    onClick = onProfileClickAction
                ) {
                    RkProfileImage(profileUrl = profileUrl)
                }
            } else {
                IconButton(
                    onClick = onProfileClickAction
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = stringResource(id = R.string.settings)
                    )
                }
            }
        }
    )
}

@Composable
fun RkTopBarTitleText() {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xffdb4437))) {
                append("R")
            }
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xff4285f4))) {
                append("eed")
            }
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xfff4b400))) {
                append("K")
            }
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xff0f9d58))) {
                append("eeper")
            }
        },
        fontSize = 22.sp,
        modifier = Modifier.clearAndSetSemantics { }
    )
}

@PreviewAnnotation
@Composable
private fun MainTopBar() {
    ReadKeeperTheme {
        Surface {
            RkMainTopBar(isLogged = true)
        }
    }
}
