package com.ilsangtech.ilsang.core.network.model.banner

import kotlinx.serialization.Serializable

@Serializable
data class NetworkBanner(
    val activeYn: String,
    val description: String,
    val id: Int,
    val image: Image,
    val title: String
)