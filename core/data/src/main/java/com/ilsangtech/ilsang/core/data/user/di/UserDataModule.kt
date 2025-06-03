package com.ilsangtech.ilsang.core.data.user.di

import com.ilsangtech.ilsang.core.data.user.datasource.UserDataSource
import com.ilsangtech.ilsang.core.data.user.datasource.UserDataSourceImpl
import com.ilsangtech.ilsang.core.data.user.repository.UserRepositoryImpl
import com.ilsangtech.ilsang.core.datastore.UserDataStore
import com.ilsangtech.ilsang.core.domain.ImageRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.network.api.AuthApiService
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
    fun provideUserDataSource(
        authApiService: AuthApiService,
        userApiService: UserApiService
    ): UserDataSource {
        return UserDataSourceImpl(
            authApiService = authApiService,
            userApiService = userApiService
        )
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userDataSource: UserDataSource,
        userDataStore: UserDataStore,
        imageRepository: ImageRepository
    ): UserRepository {
        return UserRepositoryImpl(
            userDataSource = userDataSource,
            userDataStore = userDataStore,
            imageRepository = imageRepository
        )
    }
}