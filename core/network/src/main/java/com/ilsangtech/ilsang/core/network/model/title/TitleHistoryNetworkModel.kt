package com.ilsangtech.ilsang.core.network.model.title

import kotlinx.serialization.Serializable

@Serializable
data class TitleHistoryNetworkModel(
    val id: String,
    val createdAt: String
)
