package com.github.tinkzhang.readkeeper.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.github.tinkzhang.readkeeper.R
import com.github.tinkzhang.uicomponent.PreviewAnnotation
import com.github.tinkzhang.uicomponent.RkProfileImage
import com.github.tinkzhang.uicomponent.SearchBar
import com.github.tinkzhang.uicomponent.theme.ReadKeeperTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RkMainTopBar(
    isLogged: Boolean,
    profileUrl: String = "",
    onProfileClick: () -> Unit = {},
    onSearchClick: () -> Unit = {}
) {
    var showSearch: Boolean by remember {
        mutableStateOf(false)
    }
    CenterAlignedTopAppBar(navigationIcon = {
        Image(
            painter = painterResource(id = R.drawable.ic_readkeeperlogo),
            contentDescription = "",
        )
    }, title = {
        LaunchedEffect(Unit) {
            showSearch = true
        }
        AnimatedContent(targetState = showSearch, transitionSpec = {
            fadeIn(
                animationSpec = tween(
                    durationMillis = 300, delayMillis = 700
                )
            ) with fadeOut(animationSpec = tween(durationMillis = 300, delayMillis = 500))
        }) { showSearch ->
            if (showSearch) {
                SearchBar(
                    text = R.string.home_search_bar, onClick = onSearchClick
                )
            } else {
                RkTopBarTitleText(
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }, actions = {
        if (isLogged) {
            IconButton(
                onClick = onProfileClick
            ) {
                RkProfileImage(profileUrl = profileUrl)
            }
        } else {
            IconButton(
                onClick = onProfileClick
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(id = R.string.settings)
                )
            }
        }
    })
}

@Composable
fun RkTopBarTitleText(modifier: Modifier = Modifier) {
    Text(buildAnnotatedString {
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
    }, fontSize = 22.sp, modifier = modifier.clearAndSetSemantics { }, textAlign = TextAlign.Center)
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
