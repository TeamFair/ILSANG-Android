package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.image.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ImageApiService {
    @GET("customer/image/{imageId}")
    suspend fun getImage(
        @Path("imageId") imageId: String
    ): ImageResponse
}