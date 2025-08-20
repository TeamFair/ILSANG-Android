package com.ilsangtech.ilsang.core.network.model.quest

import com.ilsangtech.ilsang.core.network.model.Pageable
import com.ilsangtech.ilsang.core.network.model.Sort
import kotlinx.serialization.Serializable

@Serializable
data class RecommendedQuestResponse(
    val content: List<RecommendedQuestNetworkModel>,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int,
    val sort: Sort,
    val totalElements: Int,
    val totalPages: Int
)