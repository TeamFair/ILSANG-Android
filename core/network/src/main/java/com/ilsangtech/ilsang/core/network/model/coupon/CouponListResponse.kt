package com.ilsangtech.ilsang.core.network.model.coupon

import kotlinx.serialization.Serializable

@Serializable
data class CouponListResponse(
    val content: List<CouponDetailNetworkModel>,
    val isLast: Boolean,
    val page: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int
)