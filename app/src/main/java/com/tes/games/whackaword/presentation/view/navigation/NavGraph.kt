package com.tes.games.whackaword.presentation.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tes.games.whackaword.presentation.view.screens.GameScreen
import com.tes.games.whackaword.presentation.view.screens.MenuScreen
import com.tes.games.whackaword.presentation.view.screens.SplashScreen
import com.tes.games.whackaword.presentation.viewmodel.VocabularyGameViewModel

@Composable
fun NavGraph(navController: NavHostController, viewModel: VocabularyGameViewModel) {
    NavHost(navController, startDestination = Screen.MenuScreen.route) {
        composable(Screen.MenuScreen.route) {
            MenuScreen(navController, viewModel =viewModel)
        }
        composable(Screen.GameScreen.route) {
           GameScreen(navController = navController, viewModel =viewModel) }
        composable(Screen.SplashScreen.route) {
            SplashScreen({},{}) }
    }
}