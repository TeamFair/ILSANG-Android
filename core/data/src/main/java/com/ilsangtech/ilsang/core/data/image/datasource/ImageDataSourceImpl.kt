package com.ilsangtech.ilsang.core.data.image.datasource

import com.ilsangtech.ilsang.core.network.api.ImageApiService
import com.ilsangtech.ilsang.core.network.model.image.ImageResponse
import javax.inject.Inject

class ImageDataSourceImpl @Inject constructor(
    private val imageApiService: ImageApiService
) : ImageDataSource {
    override suspend fun getImage(imageId: String): ImageResponse {
        return imageApiService.getImage(imageId)
    }
}