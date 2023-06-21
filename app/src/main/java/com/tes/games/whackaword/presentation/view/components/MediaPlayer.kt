package com.tes.games.whackaword.presentation.view.components

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.tes.games.whackaword.domain.model.VocabularyItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MediaPlayerComponent(
    context: Context,
    selectedVocabularyItem: VocabularyItem?,
) {
    val mediaPlayer = remember { MediaPlayer() }
    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(Unit) {

        val assetFileDescriptor =
            context.resources.openRawResourceFd(selectedVocabularyItem?.audio!!)

        mediaPlayer.setDataSource(
            assetFileDescriptor.fileDescriptor,
            assetFileDescriptor.startOffset,
            assetFileDescriptor.length
        )
        mediaPlayer.prepare()
        if (selectedVocabularyItem.image != 0) {
            mediaPlayer.start()
            coroutineScope.launch {
                delay(2000) // Delay for 1 second
                mediaPlayer.start()
            }
        } else {
            mediaPlayer.start()
        }
        onDispose {
            mediaPlayer.release()
            assetFileDescriptor.close()
        }
    }
}