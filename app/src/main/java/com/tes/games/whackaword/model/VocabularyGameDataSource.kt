package com.tes.games.whackaword.model

import com.tes.games.whackaword.R

class VocabularyGameDataSource {

   private  val vocabularyItems = listOf(
        VocabularyItem("Apple", R.drawable.fc_apple, R.raw.fc_apple),
        VocabularyItem("Banana", R.drawable.fc_banana, R.raw.fc_banana),
        VocabularyItem("Carrot", R.drawable.fc_carrot, R.raw.fc_carrot),
        VocabularyItem("Bread", R.drawable.fc_bread, R.raw.fc_bread),
        VocabularyItem("Cake", R.drawable.fc_cake, R.raw.fc_cake),
        VocabularyItem("Carrot", R.drawable.fc_carrot, R.raw.fc_carrot),
        VocabularyItem("Egg", R.drawable.fc_egg, R.raw.fc_egg),
        VocabularyItem("Orange", R.drawable.fc_orange, R.raw.fc_orange),
        VocabularyItem("Potato", R.drawable.fc_potato, R.raw.fc_potato),
        VocabularyItem("Tomato", R.drawable.fc_tomato, R.raw.fc_tomato))

    fun getVocabularyItems(): List<VocabularyItem>{
        return  vocabularyItems
    }
}