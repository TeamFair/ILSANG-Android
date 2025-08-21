package com.ilsangtech.ilsang.core.data.season.di

import com.ilsangtech.ilsang.core.data.season.datasource.SeasonDataSource
import com.ilsangtech.ilsang.core.data.season.datasource.SeasonDataSourceImpl
import com.ilsangtech.ilsang.core.data.season.repository.SeasonRepositoryImpl
import com.ilsangtech.ilsang.core.domain.SeasonRepository
import com.ilsangtech.ilsang.core.network.api.SeasonApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
data object SeasonDataModule {
    @Provides
    @Singleton
    fun provideSeasonDataSource(
        seasonApiService: SeasonApiService
    ): SeasonDataSource {
        return SeasonDataSourceImpl(seasonApiService)
    }

    @Provides
    @Singleton
    fun provideSeasonRepository(
        seasonDataSource: SeasonDataSource
    ): SeasonRepository {
        return SeasonRepositoryImpl(seasonDataSource)
    }
}