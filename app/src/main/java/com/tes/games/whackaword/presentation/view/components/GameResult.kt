package com.tes.games.whackaword.presentation.view.components
sealed class GameResult(val message: String) {
    object Win : GameResult("Congratulations! You won!")
    object Lose : GameResult("Oops! Better luck next time!")
}