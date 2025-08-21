package com.ilsangtech.ilsang.core.network.model.banner

import com.ilsangtech.ilsang.core.network.model.Pageable
import com.ilsangtech.ilsang.core.network.model.Sort
import kotlinx.serialization.Serializable

@Serializable
data class BannerResponse(
    val content: List<BannerNetworkModel>,
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