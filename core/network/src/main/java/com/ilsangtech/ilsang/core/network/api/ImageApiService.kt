package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.image.ImageResponse
import com.ilsangtech.ilsang.core.network.model.image.ImageUploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
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
    @POST("api/v1/image")
    suspend fun uploadImage(
        @Part("type") type: RequestBody,
        @Part image: MultipartBody.Part
    ): ImageUploadResponse

    @DELETE("api/v1/image")
    suspend fun deleteImage(
        @Query("id") id: String
    ): String
}