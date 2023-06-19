package com.tes.games.whackaword.presentation.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tes.games.whackaword.domain.model.VocabularyItem
import com.tes.games.whackaword.presentation.view.components.GameResult
import com.tes.games.whackaword.presentation.view.components.GameState
import com.tes.games.whackaword.presentation.view.components.VocabularyCard

@Composable
fun GameScreen(
    gameState: GameState,
    selectedVocabularyItem: VocabularyItem?,
    timerValue: Int,
    gameResult: GameResult?,
    onCardClick: (VocabularyItem) -> Unit,
    onTimerFinish: () -> Unit,
    onExitClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column {
            // Game screen content
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                when (gameState) {
                    GameState.Playing -> {
                        VocabularyCard(
                            vocabularyItem = selectedVocabularyItem,
                            onClick = { vocabularyItem ->
                                onCardClick(vocabularyItem)
                            }
                        )
                    }
                    GameState.Success -> {
                        Text(
                            text = "Congratulations! You won!",
                            fontSize = 24.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    GameState.Failure -> {
                        Text(
                            text = "Oops! Better luck next time!",
                            fontSize = 24.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }

            // Timer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Time: $timerValue",
                    fontSize = 18.sp
                )
            }

            // Game result
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                gameResult?.let {
                    Text(
                        text = it.message,
                        fontSize = 18.sp
                    )
                }
            }

            // Exit button
            Button(
                onClick = { onExitClicked() },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            ) {
                Text(text = "Exit Game")
            }
        }
    }
}