package com.ilsangtech.ilsang.core.network.model.banner

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val imageId: String,
    val location: String
)