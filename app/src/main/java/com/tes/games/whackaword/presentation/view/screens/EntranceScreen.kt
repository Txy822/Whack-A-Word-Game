package com.tes.games.whackaword.presentation.view.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EntranceScreen(
    onPlayClicked: () -> Unit,
    onExitClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Welcome to Vocabulary Learning Game",
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(
                onClick = { onPlayClicked() },
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(text = "Play Game")
            }
            Button(
                onClick = { onExitClicked() }
            ) {
                Text(text = "Exit Game")
            }
        }
    }
}