package com.tes.games.whackaword.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.tes.games.whackaword.presentation.theme.WhackAWordTheme
import com.tes.games.whackaword.presentation.view.navigation.NavGraph
import com.tes.games.whackaword.presentation.viewmodel.VocabularyGameViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhackAWordTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //VocabularyGameApp()
                    VocabularyGame()
                }
            }
        }
    }
}

@Composable
fun VocabularyGame(){
    val navController = rememberNavController()
    val viewModel : VocabularyGameViewModel= viewModel()
    NavGraph(navController, viewModel)
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WhackAWordTheme {
        Greeting("Android")
    }
}