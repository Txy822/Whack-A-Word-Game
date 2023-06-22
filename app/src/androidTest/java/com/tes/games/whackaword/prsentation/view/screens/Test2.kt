package com.tes.games.whackaword.prsentation.view.screens

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavController
import com.tes.games.whackaword.presentation.MainActivity
import com.tes.games.whackaword.presentation.VocabularyGame
import com.tes.games.whackaword.presentation.view.navigation.Screen
import com.tes.games.whackaword.presentation.view.screens.GameScreen
import com.tes.games.whackaword.presentation.view.screens.MenuScreen
import com.tes.games.whackaword.presentation.view.screens.SplashScreen
import com.tes.games.whackaword.presentation.viewmodel.VocabularyGameViewModel
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class VocabularyGameTest {

   // private val composeTestRule = createComposeRule()
   private val composeTestRule = createAndroidComposeRule<MainActivity>()


    @Before
    fun setup() {
        //composeTestRule.setContent { }
    }

    @Test
    fun testMenuScreen() {
        val navController = mock(NavController::class.java)
        val viewModel = mock(VocabularyGameViewModel::class.java)

        composeTestRule.setContent {
            MenuScreen(navController, viewModel)
        }

        // Perform your assertions or verifications here for the MenuScreen
        // Test the UI behavior or any other necessary checks
    }

    @Test
    fun testGameScreen() {
        val navController = mock(NavController::class.java)
        val viewModel = mock(VocabularyGameViewModel::class.java)

        composeTestRule.setContent {
            GameScreen(navController, viewModel)
        }

        // Perform your assertions or verifications here for the GameScreen
        // Test the UI behavior or any other necessary checks
    }

    @Test
    fun testSplashScreen() {
       // val navController = mock(NavController::class.java)

        composeTestRule.setContent {
            SplashScreen({}, {})
        }

        // Perform your assertions or verifications here for the SplashScreen
        // Test the UI behavior or any other necessary checks
    }

    @Test
    fun testVocabularyGame() {
        val navController = mock(NavController::class.java)

       // val navController = rememberNavController()
       // val viewModel = mock(VocabularyGameViewModel::class.java)

        navController.navigate(Screen.MenuScreen.route)

        composeTestRule.setContent {
            VocabularyGame()
        }

        // Perform your assertions or verifications here for the VocabularyGame
        // Test the UI behavior or any other necessary checks
    }
}