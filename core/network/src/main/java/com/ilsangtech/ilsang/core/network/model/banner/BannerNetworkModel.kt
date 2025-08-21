package com.ilsangtech.ilsang.core.network.model.banner

import kotlinx.serialization.Serializable

@Serializable
data class BannerNetworkModel(
    val id: Int,
    val bannerImageId: String,
    val description: String,
    val navigationTitle: String,
    val title: String,
    val useYn: Boolean
)