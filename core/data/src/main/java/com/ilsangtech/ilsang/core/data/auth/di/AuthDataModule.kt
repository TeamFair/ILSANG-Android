package com.ilsangtech.ilsang.core.data.auth.di

import com.ilsangtech.ilsang.core.data.auth.datasource.AuthRemoteDataSource
import com.ilsangtech.ilsang.core.data.auth.datasource.AuthRemoteDataSourceImpl
import com.ilsangtech.ilsang.core.data.auth.repository.AuthRepositoryImpl
import com.ilsangtech.ilsang.core.datastore.auth.AuthLocalDataSource
import com.ilsangtech.ilsang.core.domain.AuthRepository
import com.ilsangtech.ilsang.core.network.api.AuthApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AuthDataModule {
    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(authApiService: AuthApiService): AuthRemoteDataSource {
        return AuthRemoteDataSourceImpl(authApiService)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authLocalDataSource: AuthLocalDataSource,
        authRemoteDataSource: AuthRemoteDataSource
    ): AuthRepository {
        return AuthRepositoryImpl(
            authLocalDataSource = authLocalDataSource,
            authRemoteDataSource = authRemoteDataSource
        )
    }
}