package com.tes.games.whackaword.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tes.games.whackaword.R

@Composable
fun SplashScreen(
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
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
            Button(
                onClick = { onPlayClicked() },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Play Game")
            }
            Button(
                onClick = { onExitClicked() },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(text = "Exit Game")
            }
        }
    }
}