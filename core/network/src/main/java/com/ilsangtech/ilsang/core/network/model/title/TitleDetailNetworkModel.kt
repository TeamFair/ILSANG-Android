package com.ilsangtech.ilsang.core.network.model.title

import kotlinx.serialization.Serializable

@Serializable
data class TitleDetailNetworkModel(
    val id: String,
    val condition: String,
    val grade: String,
    val name: String,
    val type: String,
    val useYn: Boolean
)