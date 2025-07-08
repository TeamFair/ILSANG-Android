package com.ilsangtech.ilsang.core.data.image.datasource

import com.ilsangtech.ilsang.core.network.model.image.ImageResponse
import com.ilsangtech.ilsang.core.network.model.image.ImageUploadResponse

interface ImageDataSource {
    suspend fun getImage(imageId: String): ImageResponse

    suspend fun uploadImage(
        type: String,
        imageBytes: ByteArray
    ): ImageUploadResponse
}