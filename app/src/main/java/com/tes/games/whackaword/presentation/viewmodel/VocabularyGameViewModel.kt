package com.tes.games.whackaword.presentation.viewmodel

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tes.games.whackaword.domain.model.VocabularyItem
import com.tes.games.whackaword.domain.repository.VocabularyGameRepository
import com.tes.games.whackaword.presentation.view.components.GameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class VocabularyGameViewModel @Inject constructor(
    private val repository: VocabularyGameRepository,
    private val context: Context
) : ViewModel() {

    private val random = Random()
    private val holes = MutableLiveData<List<VocabularyItem>>(listOf())

    private val _point: MutableStateFlow<Int> = MutableStateFlow(0)
    val point: StateFlow<Int> = _point

    private val _level: MutableStateFlow<Int> = MutableStateFlow(0)
    val level: StateFlow<Int> = _level

    private var successCount = 0

    private val mediaPlayer = MediaPlayer()

    private val _mediaPlayerState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val mediaPlayerState: StateFlow<Boolean> = _mediaPlayerState


    private val _gameState: MutableStateFlow<GameState> = MutableStateFlow(GameState.Playing)
    val gameState: StateFlow<GameState> = _gameState

    private val _vocabularyItems: MutableStateFlow<List<VocabularyItem?>> = MutableStateFlow(
        emptyList()
    )
    val vocabularyItems: StateFlow<List<VocabularyItem?>> = _vocabularyItems

    private val _selectedVocabularyItems: MutableStateFlow<List<VocabularyItem?>> =
        MutableStateFlow(
            emptyList()
        )
    val selectedVocabularyItems: StateFlow<List<VocabularyItem?>> = _selectedVocabularyItems

    private val _targetVocabularyItem: MutableStateFlow<VocabularyItem?> = MutableStateFlow(null)
    val targetVocabularyItem: StateFlow<VocabularyItem?> = _targetVocabularyItem


    private val _resetGame: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val resetGame: StateFlow<Boolean> = _resetGame

    private val _startGame: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val startGame: StateFlow<Boolean> = _startGame

    init {
        startGame()
    }

    private fun getList() {
        _vocabularyItems.value = repository.getVocabularyItems()
    }

    private fun getSelectedVocabularyItems(stage: Int) {
        val vocabularyItemList = repository.getVocabularyItems()
        val shuffledVocabularyItems = vocabularyItemList.shuffled()
        if (stage == 3) {
            _selectedVocabularyItems.value = shuffledVocabularyItems.take(3)
        } else if (stage == 2) {
            _selectedVocabularyItems.value = shuffledVocabularyItems.take(2)
        } else {
            _selectedVocabularyItems.value = shuffledVocabularyItems.take(1)
        }
    }

    private fun getTargetVocabularyItem() {
        val vocabularyItemList = _selectedVocabularyItems.value
        val shuffledVocabularyItems = vocabularyItemList.shuffled()
        _targetVocabularyItem.value = shuffledVocabularyItems.first()
    }

    private fun playMediaVocabulary(
        context: Context,
        targetVocabularyItem: VocabularyItem = _targetVocabularyItem.value!!,
    ) {
        val assetFileDescriptor = context.resources.openRawResourceFd(targetVocabularyItem.audio)
        mediaPlayer.setDataSource(
            assetFileDescriptor.fileDescriptor,
            assetFileDescriptor.startOffset,
            assetFileDescriptor.length
        )

        mediaPlayer.start()
        _mediaPlayerState.value = true
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000) // Delay for 2 second
        }
        mediaPlayer.start()
        _mediaPlayerState.value = true
        mediaPlayer.release()
        assetFileDescriptor.close()
    }


    fun increasePoint() {

        _point.value = point.value + 1
        levelConverter(_point.value)
    }

    fun decreasePoint() {
        _point.value = point.value - 1
    }

    private fun startGame() {
        getList()
        getSelectedVocabularyItems(_level.value)
        getTargetVocabularyItem()
        playMediaVocabulary(context = context)
    }

    private fun levelConverter(point: Int){
        if (point < 3) {
           _level.value = 1
        }
        else if (point < 6) {
            _level.value = 2
        } else {
            _level.value =  3
        }
    }

    fun restartGame() {
        _resetGame.value = true
        getList()
        getSelectedVocabularyItems(_level.value)
        getTargetVocabularyItem()
    }


}