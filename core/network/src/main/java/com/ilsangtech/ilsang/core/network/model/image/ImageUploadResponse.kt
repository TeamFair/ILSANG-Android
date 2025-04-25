package com.ilsangtech.ilsang.core.network.model.image

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageUploadResponse(
    @SerialName("data") val imageUploadResponseData: ImageUploadResponseData,
    val message: String,
    val status: String
)