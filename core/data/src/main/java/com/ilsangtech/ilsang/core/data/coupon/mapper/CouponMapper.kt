package com.ilsangtech.ilsang.core.data.coupon.mapper

import com.ilsangtech.ilsang.core.model.coupon.Coupon
import com.ilsangtech.ilsang.core.network.model.coupon.CouponDetailNetworkModel

internal fun CouponDetailNetworkModel.toCoupon(): Coupon {
    return Coupon(
        id = id,
        name = coupon.name,
        storeName = coupon.storeName,
        imageId = coupon.imageId,
        couponExpireYn = couponExpireYn,
        couponUseYn = couponUseYn,
        usedAt = usedAt,
        description = coupon.description,
        validFrom = coupon.validFrom,
        validTo = coupon.validTo
    )
}