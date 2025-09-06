package com.ilsangtech.ilsang.feature.coupon.model

import com.ilsangtech.ilsang.core.model.coupon.Coupon
import com.ilsangtech.ilsang.core.util.DateConverter

data class CouponUiModel(
    val id: Int,
    val title: String,
    val writerName: String,
    val imageId: String?,
    val expireDate: String,
    val usedDate: String?,
    val isUsed: Boolean,
    val isExpired: Boolean
) {
    val isAvailable: Boolean = !isUsed && !isExpired
}

internal fun Coupon.toUiModel(): CouponUiModel {
    return CouponUiModel(
        id = id,
        title = name,
        writerName = storeName.orEmpty(),
        imageId = imageId,
        expireDate = DateConverter.formatDate(
            input = validTo,
            outputPattern = "yyyy.MM.dd HH:mm"
        ),
        usedDate = usedAt?.let {
            DateConverter.formatDate(
                input = it,
                outputPattern = "yyyy.MM.dd"
            )
        },
        isUsed = couponUseYn,
        isExpired = couponExpireYn
    )
}
