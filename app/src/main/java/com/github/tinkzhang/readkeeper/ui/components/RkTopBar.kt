package com.github.tinkzhang.readkeeper.ui.components

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.github.tinkzhang.readkeeper.R
import com.github.tinkzhang.readkeeper.user.UserViewModel
import com.github.tinkzhang.uicomponent.RkProfileImage


@Composable
fun RkMainTopBar(
    userViewModel: UserViewModel,
    onProfileClickAction: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { RkTopBarTitleText() },
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_readkeeperlogo),
                contentDescription = "",
            )
        },
        actions = {
            if (userViewModel.isSignedIn.observeAsState(false).value) {
                IconButton(onClick = onProfileClickAction) {
                    RkProfileImage(profileUrl = userViewModel.userProfileImageUrl.value)
                }
            } else {
                IconButton(onClick = onProfileClickAction) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
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
