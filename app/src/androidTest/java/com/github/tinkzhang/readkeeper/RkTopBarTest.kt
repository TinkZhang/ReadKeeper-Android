package com.github.tinkzhang.readkeeper

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import org.junit.Rule
import org.junit.Test

class RkMainTopBarTest {
    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun rkMainTopBarSignedTest() {
        composeTestRule.setContent {
        }

        composeTestRule.onRoot().printToLog("RkTopBar")

        composeTestRule
            .onNodeWithContentDescription("User Profile")
            .assertExists("Cannot find user profile node in signed in status")
            .assertHasClickAction()
    }

    @Test
    fun rkMainTopBarUnsignedTest() {
        composeTestRule.setContent {
        }

        composeTestRule
            .onNodeWithContentDescription("Settings")
            .assertExists()
            .assertHasClickAction()
    }
}
