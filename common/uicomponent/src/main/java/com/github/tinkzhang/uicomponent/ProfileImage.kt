package com.github.tinkzhang.uicomponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation

@Composable
fun RkProfileImage(
    modifier: Modifier = Modifier,
    profileUrl: String?,
    size: Dp = 36.dp,
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
    )
}
