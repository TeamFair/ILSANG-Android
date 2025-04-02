package com.ilsangtech.ilsang.core.data.banner.datasource

import com.ilsangtech.ilsang.core.network.model.banner.BannerResponse

interface BannerDataSource {
    suspend fun getBanners(): BannerResponse
}