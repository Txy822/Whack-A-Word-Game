package com.tes.games.whackaword.viewmodel

import androidx.lifecycle.ViewModel
import com.tes.games.whackaword.model.VocabularyGameRepository
import com.tes.games.whackaword.model.VocabularyItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VocabularyGameViewModel2 @Inject constructor(
    private val repository: VocabularyGameRepository
): ViewModel() {

    fun getList():List<VocabularyItem> = repository.getVocabularyItems()
}