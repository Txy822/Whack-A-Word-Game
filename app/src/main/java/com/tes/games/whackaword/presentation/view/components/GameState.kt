package com.tes.games.whackaword.presentation.view.components

sealed class GameState {
    object Playing : GameState()
    object Success : GameState()
    object Failure : GameState()
}

