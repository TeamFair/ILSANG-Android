package com.ilsangtech.ilsang.core.data.user.mapper

import com.ilsangtech.ilsang.core.model.MyInfo
import com.ilsangtech.ilsang.core.network.model.user.UserInfoResponse

fun UserInfoResponse.toMyInfo(myZoneCommercialAreaCode: String?): MyInfo {
    return MyInfo(
        id = id,
        channel = channel,
        isCommercialAreaCode = commercialAreaCode,
        myCommericalAreaCode = myZoneCommercialAreaCode,
        email = email,
        nickname = nickname,
        profileImageId = profileImageId,
        status = status,
        statusUpdatedAt = statusUpdatedAt
    )
}