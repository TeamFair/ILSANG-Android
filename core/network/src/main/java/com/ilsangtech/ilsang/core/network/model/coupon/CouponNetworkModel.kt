package com.ilsangtech.ilsang.core.network.model.coupon

import kotlinx.serialization.Serializable

@Serializable
data class CouponNetworkModel(
    val couponId: Int,
    val couponType: String,
    val storeName: String?,
    val imageId: String?,
    val name: String,
    val description: String?,
    val validFrom: String,
    val validTo: String
)