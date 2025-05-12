package com.ilsangtech.ilsang.core.data.rank.di

import com.ilsangtech.ilsang.core.data.rank.datasource.RankDataSource
import com.ilsangtech.ilsang.core.data.rank.datasource.RankDataSourceImpl
import com.ilsangtech.ilsang.core.data.rank.repository.RankRepositoryImpl
import com.ilsangtech.ilsang.core.domain.RankRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.network.api.RankApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RankDataModule {
    @Provides
    @Singleton
    fun provideRankDataSource(
        rankApiService: RankApiService
    ): RankDataSource {
        return RankDataSourceImpl(rankApiService)
    }

    @Provides
    @Singleton
    fun provideRankRepository(
        userRepository: UserRepository,
        rankDataSource: RankDataSource
    ): RankRepository {
        return RankRepositoryImpl(
            userRepository = userRepository,
            rankDataSource = rankDataSource
        )
    }

}