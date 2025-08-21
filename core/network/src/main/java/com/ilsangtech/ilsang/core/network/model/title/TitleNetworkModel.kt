package com.ilsangtech.ilsang.core.network.model.title

import kotlinx.serialization.Serializable

@Serializable
data class TitleNetworkModel(
    val name: String,
    val grade: String,
    val type: String
)
