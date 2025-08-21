package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.banner.BannerResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BannerApiService {
    @GET("api/v1/banner")
    suspend fun getBanners(
        @Query("title") title: String? = null,
        @Query("description") description: String? = null,
        @Query("useYn") userYn: Boolean? = null,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: List<String>? = null
    ): BannerResponse
}