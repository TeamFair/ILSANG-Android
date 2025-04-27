package com.ilsangtech.ilsang.core.data.image.di

import com.ilsangtech.ilsang.core.data.image.datasource.ImageDataSource
import com.ilsangtech.ilsang.core.data.image.datasource.ImageDataSourceImpl
import com.ilsangtech.ilsang.core.data.image.repository.ImageRepositoryImpl
import com.ilsangtech.ilsang.core.domain.ImageRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.network.api.ImageApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageModule {
    @Provides
    @Singleton
    fun provideImageDataSource(imageApiService: ImageApiService): ImageDataSource {
        return ImageDataSourceImpl(imageApiService)
    }

    @Provides
    @Singleton
    fun provideImageRepository(
        userRepository: UserRepository,
        imageDataSource: ImageDataSource
    ): ImageRepository {
        return ImageRepositoryImpl(
            userRepository = userRepository,
            imageDataSource = imageDataSource
        )
    }
}