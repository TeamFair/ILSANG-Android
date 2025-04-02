package com.ilsangtech.ilsang.core.data.banner.repository

import com.ilsangtech.ilsang.core.data.banner.datasource.BannerDataSource
import com.ilsangtech.ilsang.core.domain.BannerRepository
import com.ilsangtech.ilsang.core.domain.ImageRepository
import com.ilsangtech.ilsang.core.model.Banner
import javax.inject.Inject

class BannerRepositoryImpl @Inject constructor(
    private val bannerDataSource: BannerDataSource,
    private val imageRepository: ImageRepository
) : BannerRepository {
    override suspend fun getBanners(): List<Banner> {
        return bannerDataSource.getBanners().bannerList.map { networkBanner ->
            Banner(
                title = networkBanner.title,
                imageUrl = imageRepository.getImageUrl(networkBanner.image.imageId),
                description = networkBanner.description,
                activeYn = networkBanner.activeYn
            )
        }
    }
}