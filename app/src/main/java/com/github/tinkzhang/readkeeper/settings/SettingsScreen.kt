package com.github.tinkzhang.readkeeper

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.tinkzhang.readkeeper.ui.components.RkProfileImage
import com.github.tinkzhang.readkeeper.user.UserViewModel
import com.google.android.gms.common.SignInButton

@Composable
fun SettingsScreen(
    userViewModel: UserViewModel,
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 8.dp) ) {
        LoginOrProfileSection(userViewModel)

    }
}

@Composable
fun LoginOrProfileSection(userViewModel: UserViewModel) {
    val isLoggedIn:Boolean by userViewModel.isSignedIn.observeAsState(false)
    if (isLoggedIn) {
        ProfileAndLogoutSection(userViewModel = userViewModel)
    } else {
        LoginSection(userViewModel = userViewModel)
    }
}

@Composable
fun ProfileAndLogoutSection(userViewModel: UserViewModel) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp)) {
        ProfileSection(
            profileImageUrl = userViewModel.profileImageUrl,
            username = userViewModel.userName.value ?: "",
            userEmail = userViewModel.userEmail.value ?: "",
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = userViewModel::signOutWithGoogle,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Icon(Icons.Default.Logout, contentDescription = null)
            Spacer(modifier = Modifier.width(4.dp))
            Text(stringResource(id = R.string.logout))
        }
    }
}

@Composable
fun ProfileSection(
    profileImageUrl: String,
    username: String,
    userEmail: String,
) {
    Row {
        RkProfileImage(
            profileUrl = profileImageUrl,
            size = 72.dp,
            modifier = Modifier.padding(4.dp)
        )
        Column(verticalArrangement = Arrangement.SpaceAround, modifier = Modifier.padding(8.dp)) {
            Text(username)
            Spacer(modifier = Modifier.height(8.dp))
            Text(userEmail)
        }
    }
}

@Preview
@Composable
fun ProfileSectionPrew() {
    ProfileSection(
        profileImageUrl = "https://lh3.googleusercontent.com/ogw/ADea4I7Sai6ixeWECnEqktIJ3iH_Vx9YwZyM26e2Whdn_A=s192-c-mo",
        userEmail = "ZhangYunfengzju@gmail.com",
        username = "Tink Zhang",
    )

}

@Composable
fun LoginSection(userViewModel: UserViewModel) {
    AndroidView(factory = { context ->
        SignInButton(context).apply {
            setSize(SignInButton.SIZE_WIDE)
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            setOnClickListener { userViewModel.loginWithGoogle(context) }
        }
    })
}
