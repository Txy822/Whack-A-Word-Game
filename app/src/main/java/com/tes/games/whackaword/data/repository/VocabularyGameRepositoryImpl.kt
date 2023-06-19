package com.tes.games.whackaword.data.repository

import com.tes.games.whackaword.data.datasource.VocabularyGameDataSource
import com.tes.games.whackaword.domain.repository.VocabularyGameRepository
import com.tes.games.whackaword.domain.model.VocabularyItem
import javax.inject.Inject

class VocabularyGameRepositoryImpl @Inject constructor(
    private val dataSource: VocabularyGameDataSource
): VocabularyGameRepository {
    override fun getVocabularyItems():List<VocabularyItem> = dataSource.getVocabularyItems()

}