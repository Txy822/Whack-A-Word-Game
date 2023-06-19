package com.tes.games.whackaword.model

import javax.inject.Inject

class VocabularyGameRepositoryImpl @Inject constructor(
    private val dataSource:VocabularyGameDataSource
):VocabularyGameRepository{
    override fun getVocabularyItems():List<VocabularyItem> = dataSource.getVocabularyItems()

}