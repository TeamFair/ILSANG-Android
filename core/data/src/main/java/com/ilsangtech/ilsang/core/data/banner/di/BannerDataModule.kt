package com.ilsangtech.ilsang.core.data.banner.di

import com.ilsangtech.ilsang.core.data.banner.repository.BannerRepositoryImpl
import com.ilsangtech.ilsang.core.data.banner.datasource.BannerDataSource
import com.ilsangtech.ilsang.core.data.banner.datasource.BannerDataSourceImpl
import com.ilsangtech.ilsang.core.domain.BannerRepository
import com.ilsangtech.ilsang.core.domain.ImageRepository
import com.ilsangtech.ilsang.core.network.api.BannerApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object BannerDataModule {
    @Provides
    @Singleton
    fun provideBannerDataSource(bannerApiService: BannerApiService): BannerDataSource {
        return BannerDataSourceImpl(bannerApiService)
    }

    @Provides
    @Singleton
    fun provideBannerRepository(
        bannerDataSource: BannerDataSource,
        imageRepository: ImageRepository
    ): BannerRepository {
        return BannerRepositoryImpl(
            bannerDataSource = bannerDataSource,
            imageRepository = imageRepository
        )
    }
}