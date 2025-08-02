package com.ilsangtech.ilsang.core.data.title.di

import com.ilsangtech.ilsang.core.data.title.datasource.TitleDataSource
import com.ilsangtech.ilsang.core.data.title.datasource.TitleDataSourceImpl
import com.ilsangtech.ilsang.core.data.title.repository.TitleRepositoryImpl
import com.ilsangtech.ilsang.core.domain.TitleRepository
import com.ilsangtech.ilsang.core.network.api.TitleApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object TitleDataModule {
    @Provides
    @Singleton
    fun provideTitleDataSource(titleApiService: TitleApiService): TitleDataSource {
        return TitleDataSourceImpl(titleApiService)
    }

    @Provides
    @Singleton
    fun provideTitleRepository(titleDataSource: TitleDataSource): TitleRepository {
        return TitleRepositoryImpl(titleDataSource)
    }
}