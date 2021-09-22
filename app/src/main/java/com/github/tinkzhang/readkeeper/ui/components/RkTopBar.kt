package com.github.tinkzhang.readkeeper.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.github.tinkzhang.readkeeper.R
import com.github.tinkzhang.readkeeper.user.UserViewModel

@Composable
fun RkBackTopBar(title: String) {
    Row() {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = MaterialTheme.colors.primary
        )
        Text(title)
    }
}

@Composable
fun RkTopBar(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel,
    profileUrl: String? = "https://lh3.googleusercontent.com/ogw/ADea4I7Sai6ixeWECnEqktIJ3iH_Vx9YwZyM26e2Whdn_A=s192-c-mo",
    onProfileClickAction: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "",
                modifier = Modifier.size(64.dp)
            )
            RkTopBarTitleText()
        }
        if (userViewModel.isSignedIn.observeAsState(false).value) {
            RkProfileImage(
                modifier = Modifier.padding(end = 16.dp),
                profileUrl = profileUrl,
                onProfileClickAction = onProfileClickAction,
            )
        } else {
            TopBarSettingButton(
                modifier = Modifier.padding(end = 16.dp),
                onSettingClickAction = onProfileClickAction,
            )
        }
    }
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

@Composable
fun TopBarSettingButton(
    modifier: Modifier = Modifier,
    onSettingClickAction: () -> Unit,
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_settings_24),
        contentDescription = "Settings",
        modifier = modifier
            .size(35.dp)
            .clip(CircleShape)
            .clickable(onClick = onSettingClickAction),
        tint = MaterialTheme.colors.secondary,
    )
}

@Composable
fun RkProfileImage(
    modifier: Modifier = Modifier,
    profileUrl: String?,
    size: Dp = 36.dp,
    onProfileClickAction: () -> Unit = {},
) {
    Image(
        painter = rememberImagePainter(
            data = profileUrl,
            builder = {
                transformations(CircleCropTransformation())
                placeholder(R.drawable.ic_profile_24)
            }
        ),
        contentDescription = "User Profile",
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .clickable(onClick = onProfileClickAction),
    )
}

@Preview
@Composable
fun TopBarPreview() {
    val userViewModel: UserViewModel = viewModel()
    RkTopBar(userViewModel = userViewModel)
}
