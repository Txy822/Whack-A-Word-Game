package com.tes.games.whackaword.data.datasource

import com.tes.games.whackaword.domain.model.VocabularyItem
import javax.inject.Inject

class VocabularyGameDataSourceImpl @Inject constructor(
    private val constantData: ConstantDataProvider
): VocabularyGameDataSource {

    override fun  getVocabularyItems(): List<VocabularyItem>{
        return  constantData.provideConstantData()
    }
}