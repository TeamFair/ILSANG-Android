package com.ilsangtech.ilsang.core.data.auth.di

import com.ilsangtech.ilsang.core.data.auth.datasource.AuthDataSource
import com.ilsangtech.ilsang.core.data.auth.datasource.AuthDataSourceImpl
import com.ilsangtech.ilsang.core.data.auth.repository.AuthRepositoryImpl
import com.ilsangtech.ilsang.core.datastore.UserDataStore
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
    fun provideAuthDataSource(authApiService: AuthApiService): AuthDataSource {
        return AuthDataSourceImpl(authApiService)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authDataSource: AuthDataSource,
        userDataStore: UserDataStore
    ): AuthRepository {
        return AuthRepositoryImpl(
            authDataSource = authDataSource,
            userDataStore = userDataStore
        )
    }
}