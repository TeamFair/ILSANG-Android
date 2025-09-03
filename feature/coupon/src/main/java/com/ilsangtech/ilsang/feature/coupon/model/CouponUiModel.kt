package com.ilsangtech.ilsang.feature.coupon.model

data class CouponUiModel(
    val id: Int,
    val title: String,
    val writerName: String,
    val imageId: String?,
    val expireDate: String,
    val isUsed: Boolean,
    val isExpired: Boolean
)
