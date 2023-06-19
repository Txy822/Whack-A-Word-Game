package com.tes.games.whackaword.di

import com.tes.games.whackaword.model.ConstantDataProvider
import com.tes.games.whackaword.model.VocabularyGameDataSource
import com.tes.games.whackaword.model.VocabularyGameDataSourceImpl
import com.tes.games.whackaword.model.VocabularyGameRepository
import com.tes.games.whackaword.model.VocabularyGameRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class VocabularyGameModule {


    @Provides
    fun providesData(): ConstantDataProvider{
       return  ConstantDataProvider()
    }

    @Provides
    fun providesDataSource(data:ConstantDataProvider): VocabularyGameDataSource{
        return  VocabularyGameDataSourceImpl(data)
    }
    @Provides
    fun providesRepository(datasource:VocabularyGameDataSource): VocabularyGameRepository{
        return  VocabularyGameRepositoryImpl(datasource)
    }


}