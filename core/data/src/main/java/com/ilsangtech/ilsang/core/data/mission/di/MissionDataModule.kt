package com.ilsangtech.ilsang.core.data.mission.di

import com.ilsangtech.ilsang.core.data.mission.datasource.MissionDataSource
import com.ilsangtech.ilsang.core.data.mission.datasource.MissionDataSourceImpl
import com.ilsangtech.ilsang.core.data.mission.repository.MissionRepositoryImpl
import com.ilsangtech.ilsang.core.domain.MissionRepository
import com.ilsangtech.ilsang.core.network.api.MissionApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MissionDataModule {
    @Singleton
    @Provides
    fun provideMissionDataSource(missionApiService: MissionApiService): MissionDataSource {
        return MissionDataSourceImpl(missionApiService)
    }

    @Singleton
    @Provides
    fun provideMissionRepository(missionDataSource: MissionDataSource): MissionRepository {
        return MissionRepositoryImpl(missionDataSource)
    }
}