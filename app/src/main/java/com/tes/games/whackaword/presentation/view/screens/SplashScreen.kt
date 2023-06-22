package com.tes.games.whackaword.presentation.view.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tes.games.whackaword.presentation.view.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val rotation by animateFloatAsState(
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(durationMillis = 2000), RepeatMode.Restart)
    )

    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFFCCBA85)).padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        // Add your logo icon here
        Icon(
            Icons.Default.Favorite,
            contentDescription = "Logo",
            modifier = Modifier
                .size(100.dp)
                .rotate(rotation)

        )


        Text(
            text = "Whack             A Word",
            fontWeight = FontWeight.Bold,
            fontSize = 60.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        )
    }

    // Navigate to the menu screen after a delay
    LaunchedEffect(Unit) {
        delay(2000) // Delay for 2 seconds
        navController.navigate(Screen.MenuScreen.route)
    }
}