package com.ilsangtech.ilsang.core.data.area.di

import com.ilsangtech.ilsang.core.data.area.datasource.AreaDataSource
import com.ilsangtech.ilsang.core.data.area.datasource.AreaDataSourceImpl
import com.ilsangtech.ilsang.core.data.area.repository.AreaRepositoryImpl
import com.ilsangtech.ilsang.core.domain.AreaRepository
import com.ilsangtech.ilsang.core.network.api.AreaApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AreaDataModule {
    @Singleton
    @Provides
    fun provideAreaDataSource(areaApiService: AreaApiService): AreaDataSource {
        return AreaDataSourceImpl(areaApiService)
    }

    @Singleton
    @Provides
    fun provideAreaRepository(areaDataSource: AreaDataSource): AreaRepository {
        return AreaRepositoryImpl(areaDataSource)
    }
}