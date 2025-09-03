package com.ilsangtech.ilsang.feature.coupon.model

enum class CouponTab {
    Available {
        override fun toString() = "보유 중"
    },
    UsedOrExpired {
        override fun toString() = "사용 만료/완료"
    }
}