package com.tes.games.whackaword.prsentation.view.screens

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.tes.games.whackaword.presentation.MainActivity
import com.tes.games.whackaword.presentation.view.screens.VocabularyGameApp
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GameScreenUITest {
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        composeTestRule.activity.setContent {
            VocabularyGameApp()}
    }

    @Test
    fun gameScreenDisplaysCorrectly() {

       // Assert that the Points and Level  labels are  displayed
        composeTestRule.onNodeWithText("Points: ", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("0       ", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Level: ", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("0", useUnmergedTree = true).assertIsDisplayed()

    }

    @Test
    fun listAllNodesTest() {
        // Get the root node of the semantic tree
        val rootNode = composeTestRule.onRoot()
        // Recursively list all nodes in the semantic tree
        rootNode.printToLog("Node Tree")
    }

    @Test
    fun clickingOnCardIncrementsPoints() {
        // Simulate a click on the ImageCard
        // TODO: Add your click simulate here
       // composeTestRule.onNodeWithTag("ImageCard").performClick()

        // Assert that the points have been incremented
        // TODO: Add your assertions here
    }
    // Add more tests as needed for other interactions and scenarios
}