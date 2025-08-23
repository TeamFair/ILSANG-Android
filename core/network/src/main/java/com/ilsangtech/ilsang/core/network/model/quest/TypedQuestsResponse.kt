package com.ilsangtech.ilsang.core.network.model.quest

import kotlinx.serialization.Serializable

@Serializable
data class TypedQuestsResponse(
    val content: List<TypedQuestNetworkModel>,
    val page: Int,
    val size: Int,
    val totalPages: Int,
    val totalElements: Int,
    val isLast: Boolean
)
