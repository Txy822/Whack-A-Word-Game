package com.tes.games.whackaword.model

interface VocabularyGameRepository {
    fun getVocabularyItems():List<VocabularyItem>
}