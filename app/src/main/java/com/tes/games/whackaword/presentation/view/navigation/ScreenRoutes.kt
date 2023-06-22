package com.tes.games.whackaword.presentation.view.navigation

sealed class Screen(val route: String) {
    object GameScreen : Screen("game")
    object MenuScreen : Screen("menu")
    object SplashScreen : Screen("splash")
}