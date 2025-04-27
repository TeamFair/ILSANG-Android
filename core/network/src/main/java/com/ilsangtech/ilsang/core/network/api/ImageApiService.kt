package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.image.ImageResponse
import com.ilsangtech.ilsang.core.network.model.image.ImageUploadResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ImageApiService {
    @GET("customer/image/{imageId}")
    suspend fun getImage(
        @Path("imageId") imageId: String
    ): ImageResponse

    @Multipart
    @POST("customer/image")
    suspend fun uploadImage(
        @Header("Authorization") authorization: String,
        @Query("type") type: String,
        @Part image: MultipartBody.Part
    ): ImageUploadResponse
}