package com.tes.games.whackaword.presentation.viewmodel

import android.media.MediaPlayer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tes.games.whackaword.R
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
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class VocabularyGameViewModel @Inject constructor(
    private val repository: VocabularyGameRepository
) : ViewModel() {
    private val mediaPlayer = MediaPlayer()
    // val context = LocalContext.current


    private val _gameState: MutableStateFlow<GameState> = MutableStateFlow(GameState.Playing)
    val gameState: StateFlow<GameState> = _gameState

    private val _selectedVocabularyItem: MutableStateFlow<VocabularyItem?> = MutableStateFlow(null)
    val selectedVocabularyItem: StateFlow<VocabularyItem?> = _selectedVocabularyItem

    private val _timerValue2: MutableStateFlow<Int> = MutableStateFlow(0)
    val timerValue2: StateFlow<Int> = _timerValue2

    private val _gameResult: MutableStateFlow<GameResult?> = MutableStateFlow(null)
    val gameResult: StateFlow<GameResult?> = _gameResult

    private var timerJob: Job? = null
    private var timerValue = 0

    val vocabularyItems = listOf(
        VocabularyItem("Apple", R.drawable.fc_apple, R.raw.fc_apple),
        VocabularyItem("Banana", R.drawable.fc_banana, R.raw.fc_banana),
        VocabularyItem("Carrot", R.drawable.fc_carrot, R.raw.fc_carrot),
        VocabularyItem("Bread", R.drawable.fc_bread, R.raw.fc_bread),
        VocabularyItem("Cake", R.drawable.fc_cake, R.raw.fc_cake),
        VocabularyItem("Carrot", R.drawable.fc_carrot, R.raw.fc_carrot),
        VocabularyItem("Egg", R.drawable.fc_egg, R.raw.fc_egg),
        VocabularyItem("Orange", R.drawable.fc_orange, R.raw.fc_orange),
        VocabularyItem("Potato", R.drawable.fc_potato, R.raw.fc_potato),
        VocabularyItem("Tomato", R.drawable.fc_tomato, R.raw.fc_tomato),
        // Add more vocabulary items as needed
    )

    private val random = Random()
    private val holes = MutableLiveData<List<VocabularyItem>>(listOf())
    private var level = 1
    private var successCount = 0

    init {
        generateHoles()
    }

    fun startGame() {
        // Reset game state and result
        _gameState.value = GameState.Playing
        _gameResult.value = null

        // Start the timer
        timerValue = 0
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (timerValue < 10) {
                delay(1000)
                timerValue++
                _timerValue2.value = timerValue
            }
            onTimerFinish()
        }

        // Select a random vocabulary item
        selectRandomVocabularyItem()
    }

    private fun selectRandomVocabularyItem() {
        // Replace with your logic to select a random vocabulary item
        val randomItem = VocabularyItem("Word", R.drawable.fc_carrot, R.raw.fc_carrot)
        _selectedVocabularyItem.value = randomItem
    }

    fun onCardClick(vocabularyItem: VocabularyItem) {
        if (_gameState.value == GameState.Playing) {
            if (vocabularyItem == _selectedVocabularyItem.value) {
                playPositiveFeedbackSound()
                if (_timerValue2.value >= 3) {
                    _gameState.value = GameState.Success
                    _gameResult.value = GameResult.Win
                    timerJob?.cancel()
                } else {
                    selectRandomVocabularyItem()
                }
            } else {
                retreatVocabularyItems()
            }
        }
    }

    fun onTimerFinish() {
        if (_gameState.value == GameState.Playing) {
            retreatVocabularyItems()
            _gameState.value = GameState.Failure
            _gameResult.value = GameResult.Lose
        }
    }

    fun resetGame() {
        timerJob?.cancel()
        _gameResult.value = null
    }


    fun getHoles(): MutableLiveData<List<VocabularyItem>> {
        return holes
    }

    fun onHoleTapped(position: Int) {
        val selectedVocabularyItem = holes.value?.get(position)
        if (selectedVocabularyItem != null) {
            val isCorrect = checkAnswer(selectedVocabularyItem)
            if (isCorrect) {
                playPositiveFeedbackSound()
                successCount++
                if (successCount == 3) {
                    increaseLevel()
                    successCount = 0
                }
            } else {
                retreatVocabularyItems()
            }
            generateHoles()
        }
    }

    private fun generateHoles() {
        val availableVocabularyItems = mutableListOf<VocabularyItem>().apply {
            addAll(vocabularyItems)
        }

        val generatedHoles = mutableListOf<VocabularyItem>()
        for (i in 0 until level) {
            val randomIndex = random.nextInt(availableVocabularyItems.size)
            generatedHoles.add(availableVocabularyItems[randomIndex])
            availableVocabularyItems.removeAt(randomIndex)
        }

        holes.value = generatedHoles
    }

    private fun checkAnswer(selectedVocabularyItem: VocabularyItem): Boolean {
        return selectedVocabularyItem == holes.value?.firstOrNull()
    }

    private fun retreatVocabularyItems() {
        // Retreat all the vocabulary items into their holes
        val currentHoles = holes.value ?: return
        val updatedHoles = currentHoles.map { vocabularyItem ->
            vocabularyItem.copy()
        }
        holes.value = updatedHoles
    }

    private fun increaseLevel() {
        level++
    }

    private fun playPositiveFeedbackSound() {
        val positiveFeedbackSoundResourceId = R.raw.correct

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                /*
                 try {
                     mediaPlayer.reset()
                     val fileDescriptor = context.resources.openRawResourceFd(
                         positiveFeedbackSoundResourceId
                     )
                     mediaPlayer.setDataSource(
                         fileDescriptor.fileDescriptor,
                         fileDescriptor.startOffset,
                         fileDescriptor.declaredLength
                     )
                     fileDescriptor.close()
                     mediaPlayer.prepare()
                     mediaPlayer.start()
                 } catch (e: Exception) {
                     e.printStackTrace()
                 }
                 */
            }
        }
    }
}
