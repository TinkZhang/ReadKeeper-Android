package com.github.tinkzhang.readkeeper

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.github.tinkzhang.readkeeper.ui.components.RkTopBar
import org.junit.Rule
import org.junit.Test

class RkTopBarTest {
    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun rkTopBarSignedTest() {
        composeTestRule.setContent {
            RkTopBar(isSignedIn = true)
        }

        composeTestRule.onRoot().printToLog("RkTopBar")

        composeTestRule
            .onNodeWithContentDescription("User Profile")
            .assertExists("Cannot find user profile node in signed in status")
            .assertHasClickAction()
    }

    @Test
    fun rkTopBarUnsignedTest() {
        composeTestRule.setContent {
            RkTopBar(isSignedIn = false)
        }

        composeTestRule
            .onNodeWithContentDescription("Settings")
            .assertExists()
            .assertHasClickAction()
    }
}
