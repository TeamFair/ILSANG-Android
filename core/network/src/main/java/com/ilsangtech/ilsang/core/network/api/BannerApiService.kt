package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.banner.BannerResponse
import com.ilsangtech.ilsang.core.network.model.banner.BannerSearchRequest
import retrofit2.http.GET
import retrofit2.http.Query

interface BannerApiService {
    @GET("open/v1/banners")
    suspend fun getBanners(
        @Query("searchRequest") searchRequest: BannerSearchRequest,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BannerResponse
}