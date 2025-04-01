package com.ilsangtech.ilsang.core.network.model.image

import kotlinx.serialization.Serializable

@Serializable
data class NetworkImage(
    val imageId: String,
    val location: String
)