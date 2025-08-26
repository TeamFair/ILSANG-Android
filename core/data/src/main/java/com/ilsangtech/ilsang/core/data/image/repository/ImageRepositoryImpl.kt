package com.ilsangtech.ilsang.core.data.image.repository

import com.ilsangtech.ilsang.core.data.image.datasource.ImageDataSource
import com.ilsangtech.ilsang.core.domain.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageDataSource: ImageDataSource
) : ImageRepository {
    override suspend fun uploadImage(
        type: String,
        imageBytes: ByteArray
    ): String {
        return imageDataSource.uploadImage(
            type = type,
            imageBytes = imageBytes
        ).id
    }

    override suspend fun deleteImage(id: String): Result<Unit> {
        return try {
            val result = imageDataSource.deleteImage(id)
            if (result == "SUCCESS") {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to delete image"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}