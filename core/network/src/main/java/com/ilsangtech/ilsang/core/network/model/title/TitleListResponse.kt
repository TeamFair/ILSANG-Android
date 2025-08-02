package com.ilsangtech.ilsang.core.network.model.title

import kotlinx.serialization.Serializable

@Serializable
data class TitleListResponse(
    val `data`: List<TitleData>,
    val message: String,
    val status: String
)