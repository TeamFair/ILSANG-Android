package com.ilsangtech.ilsang.core.network.model.banner

import kotlinx.serialization.Serializable

@Serializable
data class BannerResponse(
    val content: List<BannerNetworkModel>,
    val totalPages: Int,
    val totalElements: Int,
    val size: Int,
    val page: Int,
    val isLast: Boolean
)