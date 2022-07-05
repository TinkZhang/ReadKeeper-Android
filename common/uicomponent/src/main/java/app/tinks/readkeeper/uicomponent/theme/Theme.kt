package app.tinks.readkeeper.uicomponent.theme

import android.os.Build
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext


// Copy from https://github.com/android/compose-samples/blob/main/Jetchat/app/src/main/java/com/example/compose/jetchat/theme/Themes.kt

private val darkColorScheme = darkColorScheme(
    primary = Purple200,
    secondary = Teal200
)

private val lightColorScheme = lightColorScheme(
    primary = Purple500,
    secondary = Teal200
)

@Composable
fun ReadKeeperTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val supportDynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val myColorScheme = when {
        supportDynamicColor && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
        supportDynamicColor && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }

    MaterialTheme(
        colorScheme = myColorScheme,
    ) {
        // TODO (M3): MaterialTheme doesn't provide LocalIndication, remove when it does
        val rippleIndication = rememberRipple()
        CompositionLocalProvider(
            LocalIndication provides rippleIndication,
            content = content
        )
    }
}
