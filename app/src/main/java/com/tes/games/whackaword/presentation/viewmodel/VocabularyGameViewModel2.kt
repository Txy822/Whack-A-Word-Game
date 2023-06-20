package com.tes.games.whackaword.presentation.viewmodel

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tes.games.whackaword.domain.model.VocabularyItem
import com.tes.games.whackaword.domain.repository.VocabularyGameRepository
import com.tes.games.whackaword.presentation.view.components.GameResult
import com.tes.games.whackaword.presentation.view.components.GameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class VocabularyGameViewModel2 @Inject constructor(
    private val repository: VocabularyGameRepository,
    private val context: Context
) : ViewModel() {

    private val random = Random()
    private val holes = MutableLiveData<List<VocabularyItem>>(listOf())

    //var level = 1
    private val _level: MutableStateFlow<Int> = MutableStateFlow(1)
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


    private val _timerValue: MutableStateFlow<Int> = MutableStateFlow(0)
    val timerValue: StateFlow<Int> = _timerValue

    private val _resetGame: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val resetGame: StateFlow<Boolean> = _resetGame

    private val _startGame: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val startGame: StateFlow<Boolean> = _startGame


    private val _gameResult: MutableStateFlow<GameResult?> = MutableStateFlow(null)
    val gameResult: StateFlow<GameResult?> = _gameResult

    private var timerJob: Job? = null
    private var timerCounter = 0

    init {
        //  playMediaVocabulary(context= context)

//        getList()
//        getSelectedVocabularyItems(3)
//        getTargetVocabularyItem()
//        playMediaVocabulary(context= context)
//        _startGame.value =true
        startGame2()
        // resetGame2()
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
            delay(2000) // Delay for 1 second
        }
        mediaPlayer.start()
        _mediaPlayerState.value = true
        mediaPlayer.release()
        assetFileDescriptor.close()
    }

    fun increaseLevel() {
        _level.value = _level.value + 1
    }

    fun decreaseLevel() {
        _level.value = _level.value - 1
    }

    fun startGame2() {
        getList()
        getSelectedVocabularyItems(3)
        getTargetVocabularyItem()
        playMediaVocabulary(context = context)
        _startGame.value = true
    }

    fun resetGame2() {
        startGame2()
        // Reset game state and result
        _gameState.value = GameState.Playing
        _gameResult.value = null

        // Start the timer
        timerCounter = 0
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (timerCounter < 10) {
                delay(1000)
                timerCounter++
                _timerValue.value = timerCounter
                _resetGame.value = true
            }
            onTimerFinish()
        }
    }

    fun startGame() {
        // Reset game state and result
        _gameState.value = GameState.Playing
        _gameResult.value = null

        // Start the timer
        timerCounter = 0
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (timerCounter < 10) {
                delay(1000)
                timerCounter++
                _timerValue.value = timerCounter
                _resetGame.value = true
            }
            onTimerFinish()
        }

        // Select a random vocabulary item
//        selectRandomVocabularyItem()
    }

    private fun onTimerFinish() {
        startGame2()
//        if (_gameState.value == GameState.Playing) {
//            retreatVocabularyItems()
//            _gameState.value = GameState.Failure
//            _gameResult.value = GameResult.Lose
//        }
    }

    fun resetGame() {
        timerJob?.cancel()
        _gameResult.value = null
    }

    private fun retreatVocabularyItems() {
        getSelectedVocabularyItems(3)
        /*
        // Retreat all the vocabulary items into their holes
        val currentHoles = holes.value ?: return
        val updatedHoles = currentHoles.map { vocabularyItem ->
            vocabularyItem.copy()
        }
        holes.value = updatedHoles

        */
    }


}