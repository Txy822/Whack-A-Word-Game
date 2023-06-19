package com.tes.games.whackaword.domain.repository

import com.tes.games.whackaword.domain.model.VocabularyItem

interface VocabularyGameRepository {
    fun getVocabularyItems():List<VocabularyItem>
}