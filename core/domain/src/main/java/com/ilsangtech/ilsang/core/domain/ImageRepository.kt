package com.ilsangtech.ilsang.core.domain

interface ImageRepository {
    suspend fun getImageUrl(imageId: String): String

    suspend fun uploadImage(
        type: String,
        imageBytes: ByteArray
    ): String

    suspend fun deleteImage(id: String): Result<Unit>
}