package com.ilsangtech.ilsang.core.network.model.coupon

import kotlinx.serialization.Serializable

@Serializable
data class CouponUpdateRequest(
    val couponUseYn: Boolean,
    val couponExpireYn: Boolean
)
