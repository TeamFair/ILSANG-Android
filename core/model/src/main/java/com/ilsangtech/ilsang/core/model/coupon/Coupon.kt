package com.ilsangtech.ilsang.core.model.coupon

data class Coupon(
    val id: Int,
    val name: String,
    val storeName: String?,
    val couponType: CouponType,
    val imageId: String?,
    val couponExpireYn: Boolean,
    val couponUseYn: Boolean,
    val usedAt: String?,
    val description: String?,
    val validFrom: String,
    val validTo: String
)
