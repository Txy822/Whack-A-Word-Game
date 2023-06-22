package com.tes.games.whackaword.presentation.viewmodel

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tes.games.whackaword.R
import com.tes.games.whackaword.domain.model.VocabularyItem
import com.tes.games.whackaword.domain.repository.VocabularyGameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VocabularyGameViewModel @Inject constructor(
    private val repository: VocabularyGameRepository,
    private val context: Context
) : ViewModel() {

   // private val random = Random()
    //private val holes = MutableLiveData<List<VocabularyItem>>(listOf())

    private val _point: MutableStateFlow<Int> = MutableStateFlow(0)
    val point: StateFlow<Int> = _point

    private val _playing: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val playing: StateFlow<Boolean> = _playing


    private val _level: MutableStateFlow<Int> = MutableStateFlow(0)
    val level: StateFlow<Int> = _level

    //private var successCount = 0

    var mediaPlayer = MediaPlayer()

//    private val _mediaPlayerState: MutableStateFlow<Boolean> = MutableStateFlow(false)
//    val mediaPlayerState: StateFlow<Boolean> = _mediaPlayerState


//    private val _gameState: MutableStateFlow<GameState> = MutableStateFlow(GameState.Playing)
//    val gameState: StateFlow<GameState> = _gameState

    private val _vocabularyItems: MutableStateFlow<List<VocabularyItem?>> = MutableStateFlow(
        emptyList()
    )
    val vocabularyItems: StateFlow<List<VocabularyItem?>> = _vocabularyItems

    private val _selectedVocabularyItems: MutableStateFlow<List<VocabularyItem?>> =
        MutableStateFlow(
            emptyList()
        )
    val selectedVocabularyItems: StateFlow<List<VocabularyItem?>> = _selectedVocabularyItems

    private val _targetVocabularyItem: MutableStateFlow<VocabularyItem?> = MutableStateFlow(
       null
    )
    val targetVocabularyItem: StateFlow<VocabularyItem?> = _targetVocabularyItem


    private val _resetGame: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val resetGame: StateFlow<Boolean> = _resetGame

//    private val _startGame: MutableStateFlow<Boolean> = MutableStateFlow(false)
//    val startGame: StateFlow<Boolean> = _startGame

    init {
        startGame()
    }

    fun setPlaying(){
        _playing.value =true
    }

    private fun getList() {
        _vocabularyItems.value = repository.getVocabularyItems()
    }

     fun getSelectedVocabularyItemsAndTarget(stage: Int) {
        val vocabularyItemList = repository.getVocabularyItems()
         if( vocabularyItemList.isNotEmpty()) {
             val shuffledVocabularyItems = vocabularyItemList.shuffled()
             val selectOne = shuffledVocabularyItems.take(1)
             val targetFromOne = selectOne.first()
             val selectTwo = shuffledVocabularyItems.take(2)
             val targetFromTwo = selectTwo.shuffled().first()
             val selectThree = shuffledVocabularyItems.take(3)
             val targetFromThree = selectThree.shuffled().first()
             if (stage == 3) {
                 _selectedVocabularyItems.value = selectThree
                 _targetVocabularyItem.value = targetFromThree
             } else if (stage == 2) {
                 _selectedVocabularyItems.value = selectTwo
                 _targetVocabularyItem.value = targetFromTwo
             } else {
                 _selectedVocabularyItems.value = selectOne
                 _targetVocabularyItem.value = targetFromOne
             }
         }
    }

//     fun getTargetVocabularyItem() {
//        val vocabularyItemList = _selectedVocabularyItems.value
//        if (vocabularyItemList.isNotEmpty()) {
//            val shuffledVocabularyItems = vocabularyItemList.shuffled()
//            _targetVocabularyItem.value = shuffledVocabularyItems.first()
//        } else {
//            _targetVocabularyItem.value = null
//        }
//    }

    private fun playMediaVocabulary(
        context: Context,
        targetVocabularyItem: VocabularyItem? = _targetVocabularyItem.value,
    ) {
        if (targetVocabularyItem != null) {
            val resources = context.resources ?: return // Check if resources are null
            val assetFileDescriptor =
                resources.openRawResourceFd(targetVocabularyItem.audio)
            mediaPlayer.setDataSource(
                assetFileDescriptor.fileDescriptor,
                assetFileDescriptor.startOffset,
                assetFileDescriptor.length
            )

            mediaPlayer.start()

            viewModelScope.launch(Dispatchers.IO) {
                delay(2000) // Delay for 2 second
            }
            mediaPlayer.start()

            mediaPlayer.release()
            assetFileDescriptor.close()
        } else {
            val resources = context.resources ?: return // Check if resources are null
            val assetFileDescriptor =
                resources.openRawResourceFd(VocabularyItem("Apple", R.drawable.fc_apple, R.raw.fc_apple).audio)
            mediaPlayer.setDataSource(
                assetFileDescriptor.fileDescriptor,
                assetFileDescriptor.startOffset,
                assetFileDescriptor.length
            )

            mediaPlayer.start()

            viewModelScope.launch(Dispatchers.IO) {
                delay(2000) // Delay for 2 second
            }
            mediaPlayer.start()

            mediaPlayer.release()
            assetFileDescriptor.close()
        }
    }


    fun increasePoint() {

        _point.value = point.value + 1
        levelConverter(_point.value)
    }

    fun decreasePoint() {
        _point.value = point.value - 1
    }

    fun startGame() {
        getList()
        getSelectedVocabularyItemsAndTarget(_level.value)
       // getTargetVocabularyItem()
        playMediaVocabulary(context = context)
    }

    private fun levelConverter(point: Int) {
        if (point < 3) {
            _level.value = 1
        } else if (point < 6) {
            _level.value = 2
        } else {
            _level.value = 3
        }
    }

    fun restartGame() {
        _resetGame.value = true
//        getList()
        getSelectedVocabularyItemsAndTarget(_level.value)
//        getTargetVocabularyItem()
    }


}