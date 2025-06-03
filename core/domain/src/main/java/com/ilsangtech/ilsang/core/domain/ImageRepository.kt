package com.ilsangtech.ilsang.core.domain

interface ImageRepository {
    suspend fun getImageUrl(imageId: String): String

    suspend fun uploadImage(
        authorization: String,
        type: String,
        imageBytes: ByteArray
    ): String
}