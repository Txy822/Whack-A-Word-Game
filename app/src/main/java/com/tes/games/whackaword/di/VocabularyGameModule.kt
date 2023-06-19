package com.tes.games.whackaword.di

import android.app.Application
import android.content.Context
import com.tes.games.whackaword.data.datasource.ConstantDataProvider
import com.tes.games.whackaword.data.datasource.VocabularyGameDataSource
import com.tes.games.whackaword.data.datasource.VocabularyGameDataSourceImpl
import com.tes.games.whackaword.data.repository.VocabularyGameRepositoryImpl
import com.tes.games.whackaword.domain.repository.VocabularyGameRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class VocabularyGameModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application
    }

    @Provides
    fun providesData(): ConstantDataProvider {
       return  ConstantDataProvider()
    }

    @Provides
    fun providesDataSource(data: ConstantDataProvider): VocabularyGameDataSource {
        return  VocabularyGameDataSourceImpl(data)
    }
    @Provides
    fun providesRepository(datasource: VocabularyGameDataSource): VocabularyGameRepository {
        return  VocabularyGameRepositoryImpl(datasource)
    }


}