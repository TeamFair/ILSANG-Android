package com.ilsangtech.ilsang.core.network.model.title

import kotlinx.serialization.Serializable

@Serializable
data class TitleNetworkModel(
    val id: String,
    val name: String,
    val type: String,
    val condition: String?,
    val createdAt: String
)
