package com.ilsangtech.ilsang.core.data.image.datasource

import com.ilsangtech.ilsang.core.network.api.ImageApiService
import com.ilsangtech.ilsang.core.network.model.image.ImageResponse
import com.ilsangtech.ilsang.core.network.model.image.ImageUploadResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class ImageDataSourceImpl @Inject constructor(
    private val imageApiService: ImageApiService
) : ImageDataSource {
    override suspend fun getImage(imageId: String): ImageResponse {
        return imageApiService.getImage(imageId)
    }

    override suspend fun uploadImage(
        authorization: String,
        type: String,
        imageBytes: ByteArray
    ): ImageUploadResponse {
        val requestBody = imageBytes.toRequestBody("image/*".toMediaType())
        return imageApiService.uploadImage(
            authorization = authorization,
            type = type,
            image = MultipartBody.Part.createFormData(
                name = "file",
                filename = "image.jpg",
                body = requestBody
            )
        )
    }
}