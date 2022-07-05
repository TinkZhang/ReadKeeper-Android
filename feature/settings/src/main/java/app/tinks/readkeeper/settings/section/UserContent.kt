package app.tinks.readkeeper.settings.section

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.settings.R
import app.tinks.readkeeper.uicomponent.RkProfileImage

@ExperimentalMaterial3Api
@Composable
fun ProfileAndLogoutSection(
    profileImageUrl: String = "",
    username: String = "",
    userEmail: String = "",
    onLogoutClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ProfileSection(
                profileImageUrl = profileImageUrl,
                username = username,
                userEmail = userEmail,
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = onLogoutClick,
                modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(0.8f)
            ) {
                Icon(Icons.Default.Logout, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(stringResource(R.string.logout))
            }
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
            Text(text = username, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(userEmail)
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun ProfileAndLogoutSectionPreview() {
    ProfileAndLogoutSection(
        profileImageUrl = "https://lh3.googleusercontent.com/ogw/ADea4I7Sai6ixeWECnEqktIJ3iH_Vx9YwZyM26e2Whdn_A=s192-c-mo",
        userEmail = "ZhangYunfengzju@gmail.com",
        username = "Tink Zhang",
    )
}
