package com.ilsangtech.ilsang.core.network.model.coupon

import kotlinx.serialization.Serializable

@Serializable
data class CouponPasswordVerifyRequest(val password: String)
