package com.tes.games.whackaword.prsentation.view.screens

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.tes.games.whackaword.presentation.MainActivity
import com.tes.games.whackaword.presentation.view.screens.GameScreen
import com.tes.games.whackaword.presentation.view.screens.MenuScreen
import org.junit.Rule
import org.junit.Test

class GameScreenUITest {
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun gameScreenDisplaysCorrectly() {
        composeTestRule.activity.setContent {
            GameScreen()
        }
       // Assert that the Points and Level  labels are  displayed
        composeTestRule.onNodeWithText("Points: ", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Exit", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("0       ", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Level: ", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("0", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun menuScreenDisplaysCorrectly() {
        composeTestRule.activity.setContent {
            MenuScreen()
        }
        // Assert that the Points and Level  labels are  displayed
        composeTestRule.onNodeWithText("Whack A Word", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Level: 0   Score: 0", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("This game has vocabulary lists to learn easily. Cards pops up with image of a word. When you hear the vocabulary from the audio tap it. it repeats again before retreats to the hole. Whenever you clicked the right word you will have one point. But if you clicked wrong word you will lose one point. As you progress the level increases. After three successful answer the level will increase by one until level three. Enjoy ", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Start", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun listAllNodesTest() {
        composeTestRule.activity.setContent {
            GameScreen()
        }
        // Get the root node of the semantic tree
        val rootNode = composeTestRule.onRoot()
        // Recursively list all nodes in the semantic tree
        rootNode.printToLog("Node Tree")
    }

    @Test
    fun clickingOnCardIncrementsPoints() {
        composeTestRule.activity.setContent {
            GameScreen()
        }
        // Simulate a click on the ImageCard
        // TODO: Add your click simulate here
       // composeTestRule.onNodeWithTag("ImageCard").performClick()

        // Assert that the points have been incremented
        // TODO: Add your assertions here
    }
    // Add more tests as needed for other interactions and scenarios
}