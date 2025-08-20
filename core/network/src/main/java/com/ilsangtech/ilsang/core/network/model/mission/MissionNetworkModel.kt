package com.ilsangtech.ilsang.core.network.model.mission

import kotlinx.serialization.Serializable

@Serializable
data class MissionNetworkModel(
    val exampleImageIds: List<String>,
    val id: Int,
    val title: String,
    val type: String
)