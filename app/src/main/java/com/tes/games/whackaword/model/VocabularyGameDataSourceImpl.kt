package com.tes.games.whackaword.model

import javax.inject.Inject

class VocabularyGameDataSourceImpl @Inject constructor(
    private val constantData: ConstantDataProvider
):VocabularyGameDataSource  {

    override fun  getVocabularyItems(): List<VocabularyItem>{
        return  constantData.provideConstantData()
    }
}