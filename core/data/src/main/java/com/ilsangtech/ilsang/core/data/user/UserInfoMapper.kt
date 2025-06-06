package com.ilsangtech.ilsang.core.data.user

import com.ilsangtech.ilsang.core.model.UserInfo
import com.ilsangtech.ilsang.core.network.model.user.UserInfoNetworkModel

internal fun UserInfoNetworkModel.toUserInfo(): UserInfo {
    return UserInfo(
        completeChallengeCount = completeChallengeCount,
        couponCount = couponCount,
        nickname = nickname,
        profileImage = profileImage,
        status = status,
        xpPoint = xpPoint
    )
}