package com.ilsangtech.ilsang.core.network.model.quest

import kotlinx.serialization.Serializable

@Serializable
data class PageableRequest(
    val page: Int? = null,
    val size: Int? = null,
    val sort: List<String>? = null
)