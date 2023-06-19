package com.tes.games.whackaword.presentation.view.components

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.tes.games.whackaword.domain.model.VocabularyItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MediaPlayerComponent(context: Context, selectedVocabularyItem: VocabularyItem?, play: Boolean) {
    val mediaPlayer = remember { MediaPlayer() }
    val isPlaying = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    mediaPlayer.setOnCompletionListener {
        isPlaying.value = false
    }

    DisposableEffect(Unit) {
        val assetFileDescriptor = context.resources.openRawResourceFd(selectedVocabularyItem?.audio!!)
        mediaPlayer.setDataSource(
            assetFileDescriptor.fileDescriptor,
            assetFileDescriptor.startOffset,
            assetFileDescriptor.length
        )
        mediaPlayer.prepare()
        mediaPlayer.start()

        coroutineScope.launch {
            delay(2000) // Delay for 1 second
            mediaPlayer.start()
        }
        onDispose {
            mediaPlayer.release()
            assetFileDescriptor.close()
        }
    }

}

/*
package com.tes.games.whackaword.view

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tes.games.whackaword.R
import com.tes.games.whackaword.model.VocabularyItem

@Composable
fun MediaPlayerComponent(context: Context, selectedVocabularyItem: VocabularyItem) {
    val mediaPlayer = remember { MediaPlayer() }
    val isPlaying = remember { mutableStateOf(false) }

    mediaPlayer.setOnCompletionListener {
        isPlaying.value = false
    }

    DisposableEffect(Unit) {
        val assetFileDescriptor = context.resources.openRawResourceFd(R.raw.fc_apple)
        mediaPlayer.setDataSource(
            assetFileDescriptor.fileDescriptor,
            assetFileDescriptor.startOffset,
            assetFileDescriptor.length
        )
        mediaPlayer.prepare()

        onDispose {
            mediaPlayer.release()
            assetFileDescriptor.close()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Simple Media Player", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (isPlaying.value) {
                    mediaPlayer.pause()
                    isPlaying.value = false
                } else {
                    mediaPlayer.start()
                    isPlaying.value = true
                }
            }
        ) {
            Text(if (isPlaying.value) "Pause" else "Play")
        }
    }
}

 */