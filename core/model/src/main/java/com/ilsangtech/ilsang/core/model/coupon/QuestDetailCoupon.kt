package com.ilsangtech.ilsang.core.model.coupon

data class QuestDetailCoupon(
    val id: Int,
    val name: String,
    val imageId: String?,
    val validTo: String,
    val storeName: String?,
    val description: String?
)
