package com.ilsangtech.ilsang.core.data.user.di

import com.ilsangtech.ilsang.core.data.user.datasource.UserRemoteDataSource
import com.ilsangtech.ilsang.core.data.user.datasource.UserRemoteDataSourceImpl
import com.ilsangtech.ilsang.core.data.user.repository.UserRepositoryImpl
import com.ilsangtech.ilsang.core.datastore.user.UserLocalDataSource
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.network.api.UserApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserDataModule {
    @Provides
    @Singleton
    fun provideUserRemoteDataSource(userApiService: UserApiService): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(userApiService)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userLocalDataSource: UserLocalDataSource,
        userRemoteDataSource: UserRemoteDataSource
    ): UserRepository {
        return UserRepositoryImpl(
            userLocalDataSource = userLocalDataSource,
            userRemoteDataSource = userRemoteDataSource
        )
    }
}