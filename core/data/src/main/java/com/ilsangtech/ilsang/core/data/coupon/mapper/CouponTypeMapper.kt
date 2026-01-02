package com.ilsangtech.ilsang.core.data.coupon.mapper

import com.ilsangtech.ilsang.core.model.coupon.CouponType

internal fun String.toCouponType(): CouponType {
    return when (this) {
        "WEEK" -> CouponType.Week
        "MONTH" -> CouponType.Month
        "SEASON" -> CouponType.Season
        "REALTIME" -> CouponType.RealTime
        else -> throw IllegalArgumentException("Unknown coupon type: $this")
    }
}