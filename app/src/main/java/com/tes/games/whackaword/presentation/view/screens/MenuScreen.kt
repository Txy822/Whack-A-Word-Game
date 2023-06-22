package com.tes.games.whackaword.presentation.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tes.games.whackaword.R
import com.tes.games.whackaword.presentation.view.navigation.Screen
import com.tes.games.whackaword.presentation.viewmodel.VocabularyGameViewModel

@Composable
fun MenuScreen(
    navController: NavController= rememberNavController(),
    viewModel: VocabularyGameViewModel = viewModel()
) {
    //val viewModel: VocabularyGameViewModel = viewModel()
    val level by viewModel.level.collectAsState()
    val score by viewModel.point.collectAsState()
    val gameInProgress by viewModel.playing.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCCBA85)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Whack A Word",
            fontWeight = FontWeight.Bold,
            fontSize = 60.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.screenshot),
                contentDescription = null,
                modifier = Modifier
                    .height(300.dp)
                    .width(400.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = Color(0xFFCCBA85))
            )
        }

        // Level and Score Box
        Box(
            modifier = Modifier
                .height(60.dp)
                .width(400.dp)
                .background(color = colorResource(id = R.color.sky)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Level: $level   Score: $score",
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
        // Instruction Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 100.dp, end = 100.dp, top = 20.dp, bottom = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "This game has vocabulary lists to learn easily. Cards pops up with image of a word. When you hear the vocabulary from the audio tap it. it repeats again before retreats to the hole. Whenever you clicked the right word you will have one point. But if you clicked wrong word you will lose one point. As you progress the level increases. After three successful answer the level will increase by one until level three. Enjoy ",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            )
        }



        Spacer(modifier = Modifier.height(32.dp))

        // Start Game Button
        Button(
            onClick = { navController.navigate(Screen.GameScreen.route)
                       viewModel.setPlaying()
                      },
            modifier = Modifier
                .width(300.dp)
                .height(70.dp)
        ) {
            Text(text = if (gameInProgress && (score!=0 && level !=0)) "Resume" else "Start",
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                color = Color.Black,)
        }
    }

}