package com.tes.games.whackaword.viewmodel

import androidx.lifecycle.ViewModel
import com.tes.games.whackaword.model.VocabularyGameRepository
import javax.inject.Inject

class VocabularyGameViewModel2 @Inject constructor(
    private val repository: VocabularyGameRepository
): ViewModel() {
}