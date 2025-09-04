package com.ilsangtech.ilsang.core.model.coupon

sealed interface CouponType {
    data object Week : CouponType
    data object Month : CouponType
    data object Season : CouponType
}