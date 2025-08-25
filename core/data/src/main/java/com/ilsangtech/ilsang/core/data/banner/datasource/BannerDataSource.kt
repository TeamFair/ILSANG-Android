package com.ilsangtech.ilsang.core.data.banner.datasource

import com.ilsangtech.ilsang.core.network.model.banner.BannerNetworkModel

interface BannerDataSource {
    suspend fun getBanners(): List<BannerNetworkModel>
}