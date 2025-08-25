package com.ilsangtech.ilsang.core.data.banner.datasource

import com.ilsangtech.ilsang.core.network.api.BannerApiService
import com.ilsangtech.ilsang.core.network.model.banner.BannerNetworkModel
import javax.inject.Inject

class BannerDataSourceImpl @Inject constructor(
    private val bannerApiService: BannerApiService
) : BannerDataSource {
    override suspend fun getBanners(): List<BannerNetworkModel> {
        return bannerApiService.getBanners()
    }
}