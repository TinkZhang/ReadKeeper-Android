package app.tinks.readkeeper

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import app.tinks.readkeeper.navigation.RkNavHost
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            RkNavHost(navController = navController)
        }
    }

    @Test
    fun `navigation-verify-start-destination`() {
        composeTestRule
            .onNodeWithContentDescription("ReadKeeper Logo")
            .assertIsDisplayed()
    }
}