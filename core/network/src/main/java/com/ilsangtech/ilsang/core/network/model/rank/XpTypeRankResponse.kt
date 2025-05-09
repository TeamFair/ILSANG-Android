package com.ilsangtech.ilsang.core.network.model.rank

import kotlinx.serialization.Serializable

@Serializable
data class XpTypeRankResponse(
    val `data`: List<XpTypeRankNetworkModel>,
    val message: String,
    val status: String
)