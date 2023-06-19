package com.tes.games.whackaword.util

sealed class Screen {
    object SplashScreen : Screen()
    object EntranceScreen : Screen()
    object GameScreen : Screen()
}
