package com.ilsangtech.ilsang.core.data.user.mapper

import com.ilsangtech.ilsang.core.model.user.MyInfo
import com.ilsangtech.ilsang.core.model.user.UserInfo

fun UserInfo.toMyInfo(
    totalPoint: Int,
    myZoneCommercialAreaCode: String,
    showIsZoneDialogAgain: Boolean,
    shouldShowSeasonOpenDialog: Boolean
): MyInfo {
    return MyInfo(
        id = id,
        channel = channel,
        totalPoint = totalPoint,
        isCommercialAreaCode = commercialAreaCode,
        myCommericalAreaCode = myZoneCommercialAreaCode,
        email = email,
        nickname = nickname,
        profileImageId = profileImageId,
        status = status,
        statusUpdatedAt = statusUpdatedAt,
        title = userTitle,
        showIsZoneDialogAgain = showIsZoneDialogAgain,
        shouldShowSeasonOpenDialog = shouldShowSeasonOpenDialog
    )
}