package com.ilsangtech.ilsang.core.data.image.datasource

import com.ilsangtech.ilsang.core.network.model.image.ImageResponse

interface ImageDataSource {
    suspend fun getImage(imageId: String): ImageResponse
}