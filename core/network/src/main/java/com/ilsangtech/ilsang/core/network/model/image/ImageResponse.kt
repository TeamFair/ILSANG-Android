package com.ilsangtech.ilsang.core.network.model.image

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    @SerialName("data") val networkImage: NetworkImage,
    val message: String,
    val status: String
)