package com.tes.games.whackaword.model

interface VocabularyGameDataSource {
   fun  getVocabularyItems(): List<VocabularyItem>
}
