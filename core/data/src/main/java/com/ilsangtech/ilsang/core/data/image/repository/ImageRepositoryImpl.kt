package com.ilsangtech.ilsang.core.data.image.repository

import com.ilsangtech.ilsang.core.data.image.datasource.ImageDataSource
import com.ilsangtech.ilsang.core.domain.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageDataSource: ImageDataSource
) : ImageRepository {
    override suspend fun getImageUrl(imageId: String): String {
        return imageDataSource.getImage(imageId).networkImage.location
    }

    override suspend fun uploadImage(
        type: String,
        imageBytes: ByteArray
    ): String {
        return imageDataSource.uploadImage(
            type = type,
            imageBytes = imageBytes
        ).id
    }
}