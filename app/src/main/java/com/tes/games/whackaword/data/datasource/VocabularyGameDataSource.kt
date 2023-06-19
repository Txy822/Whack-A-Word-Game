package com.tes.games.whackaword.data.datasource

import com.tes.games.whackaword.domain.model.VocabularyItem

interface VocabularyGameDataSource {
   fun  getVocabularyItems(): List<VocabularyItem>
}
