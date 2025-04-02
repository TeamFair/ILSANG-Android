package com.ilsangtech.ilsang.core.network.model.banner

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BannerResponse(
    @SerialName("data") val bannerList: List<NetworkBanner>,
    val message: String,
    val page: Int,
    val size: Int,
    val status: String,
    val total: Int
)