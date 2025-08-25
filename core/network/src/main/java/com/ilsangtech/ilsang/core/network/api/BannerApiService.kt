package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.banner.BannerNetworkModel
import retrofit2.http.GET

interface BannerApiService {
    @GET("api/v1/banner/user")
    suspend fun getBanners(): List<BannerNetworkModel>
}