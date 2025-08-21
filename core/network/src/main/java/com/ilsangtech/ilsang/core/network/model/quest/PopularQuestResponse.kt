package com.ilsangtech.ilsang.core.network.model.quest

import com.ilsangtech.ilsang.core.network.model.Pageable
import com.ilsangtech.ilsang.core.network.model.Sort
import kotlinx.serialization.Serializable

@Serializable
data class PopularQuestResponse(
    val content: List<PopularQuestNetworkModel>,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int,
    val sort: Sort,
    val pageable: Pageable,
    val numberOfElements: Int,
    val empty: Boolean
)