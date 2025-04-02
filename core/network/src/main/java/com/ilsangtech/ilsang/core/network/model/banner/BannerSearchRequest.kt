package com.ilsangtech.ilsang.core.network.model.banner

import kotlinx.serialization.Serializable

@Serializable
data class BannerSearchRequest(
    val titleLike: String? = null,
    val descriptionLike: String? = null,
    val activeYn: String? = null
)
