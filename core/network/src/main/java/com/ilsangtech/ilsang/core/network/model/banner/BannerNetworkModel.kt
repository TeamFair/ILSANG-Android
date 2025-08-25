package com.ilsangtech.ilsang.core.network.model.banner

import kotlinx.serialization.Serializable

@Serializable
data class BannerNetworkModel(
    val id: Int,
    val title: String,
    val navigationTitle: String,
    val bannerImageId: String,
    val description: String
)