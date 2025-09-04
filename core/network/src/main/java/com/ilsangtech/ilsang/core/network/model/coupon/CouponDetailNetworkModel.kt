package com.ilsangtech.ilsang.core.network.model.coupon

import kotlinx.serialization.Serializable

@Serializable
data class CouponDetailNetworkModel(
    val id: Int,
    val userId: String,
    val coupon: CouponNetworkModel,
    val couponExpireYn: Boolean,
    val couponUseYn: Boolean,
    val usedAt: String?
)