package com.ilsangtech.ilsang.core.domain

interface ImageRepository {
    suspend fun uploadImage(
        type: String,
        imageBytes: ByteArray
    ): String

    suspend fun uploadProfileImage(imageBytes: ByteArray): Result<String>

    suspend fun uploadMissionImage(imageBytes: ByteArray): Result<String>

    suspend fun deleteImage(id: String): Result<Unit>
}