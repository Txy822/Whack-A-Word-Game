package com.tes.games.whackaword.model

import javax.inject.Inject

class VocabularyGameRepository @Inject constructor(
    private val dataSource:VocabularyGameDataSource
){


}