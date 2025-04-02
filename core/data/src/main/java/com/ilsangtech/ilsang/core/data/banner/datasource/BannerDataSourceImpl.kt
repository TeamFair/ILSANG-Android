package com.ilsangtech.ilsang.core.data.banner.datasource

import com.ilsangtech.ilsang.core.network.api.BannerApiService
import com.ilsangtech.ilsang.core.network.model.banner.BannerResponse
import com.ilsangtech.ilsang.core.network.model.banner.BannerSearchRequest
import javax.inject.Inject

class BannerDataSourceImpl @Inject constructor(
    private val bannerApiService: BannerApiService
) : BannerDataSource {
    override suspend fun getBanners(): BannerResponse {
        return bannerApiService.getBanners(
            searchRequest = BannerSearchRequest(),
            page = 0,
            size = 10
        )
    }
}