package com.ilsangtech.ilsang.core.data.challenge.di

import com.ilsangtech.ilsang.core.data.challenge.datasource.ChallengeDataSource
import com.ilsangtech.ilsang.core.data.challenge.datasource.ChallengeDataSourceImpl
import com.ilsangtech.ilsang.core.data.challenge.repository.ChallengeRepositoryImpl
import com.ilsangtech.ilsang.core.domain.ChallengeRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.network.api.ChallengeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChallengeDataModule {
    @Provides
    @Singleton
    fun provideChallengeDataSource(challengeApiService: ChallengeApiService): ChallengeDataSource {
        return ChallengeDataSourceImpl(challengeApiService)
    }

    @Provides
    @Singleton
    fun provideChallengeRepository(
        challengeDataSource: ChallengeDataSource,
        userRepository: UserRepository
    ): ChallengeRepository {
        return ChallengeRepositoryImpl(
            challengeDataSource = challengeDataSource,
            userRepository = userRepository
        )
    }
}