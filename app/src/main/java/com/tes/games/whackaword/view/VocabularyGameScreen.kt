package com.tes.games.whackaword.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tes.games.whackaword.R
import com.tes.games.whackaword.model.VocabularyItem
import com.tes.games.whackaword.util.Screen
import com.tes.games.whackaword.viewmodel.VocabularyGameViewModel

@Composable
fun VocabularyGameApp() {
    val viewModel: VocabularyGameViewModel = viewModel()


    // Track the current game screen
    var currentScreen by remember { mutableStateOf(Screen.EntranceScreen) }

    // Observe the game state from the ViewModel
    val gameState by viewModel.gameState.collectAsState()

    // Observe the selected vocabulary item from the ViewModel
    val selectedVocabularyItem by viewModel.selectedVocabularyItem.collectAsState()

    // Observe the timer value from the ViewModel
    val timerValue by viewModel.timerValue2.collectAsState()

    // Observe the game result from the ViewModel
    val gameResult by viewModel.gameResult.collectAsState()

    GameScreen2()
    val context = LocalContext.current
    val item =  VocabularyItem("",0, R.raw.fc_apple)

   // MediaPlayerComponent(context, item,true)


        // HoleScreen()
    /*
    SplashScreen(
        onPlayClicked = {
            currentScreen = Screen.EntranceScreen
        },
        onExitClicked = { }
    )
*/
    /*
    when (currentScreen) {
        Screen.SplashScreen -> SplashScreen(
            onPlayClicked = {
                currentScreen = Screen.EntranceScreen
            },
            onExitClicked = { }
        )

        Screen.EntranceScreen -> EntranceScreen(
            onPlayClicked = {
                currentScreen = Screen.GameScreen
                viewModel.startGame()
            },
            onExitClicked = {
//                finish()
            }
        )

        Screen.GameScreen -> GameScreen(
            gameState = gameState,
            selectedVocabularyItem = selectedVocabularyItem,
            timerValue = timerValue,
            gameResult = gameResult,
            onCardClick = { vocabularyItem ->
                viewModel.onCardClick(vocabularyItem)
            },
            onTimerFinish = { viewModel.onTimerFinish() },
            onExitClicked = {
                currentScreen = Screen.EntranceScreen
                viewModel.resetGame()
            }
        )
    }
    */
}

sealed class GameState {
    object Playing : GameState()
    object Success : GameState()
    object Failure : GameState()
}

sealed class GameResult(val message: String) {
    object Win : GameResult("Congratulations! You won!")
    object Lose : GameResult("Oops! Better luck next time!")
}