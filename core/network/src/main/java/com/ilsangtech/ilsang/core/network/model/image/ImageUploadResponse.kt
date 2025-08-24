package com.ilsangtech.ilsang.core.network.model.image

import kotlinx.serialization.Serializable

@Serializable
data class ImageUploadResponse(
    val fileName: String,
    val fileSize: Int,
    val id: String,
    val type: String
)