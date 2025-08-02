package com.ilsangtech.ilsang.core.data.user

import com.ilsangtech.ilsang.core.model.Title
import com.ilsangtech.ilsang.core.model.UserInfo
import com.ilsangtech.ilsang.core.network.model.user.UserInfoNetworkModel

internal fun UserInfoNetworkModel.toUserInfo(): UserInfo {
    return UserInfo(
        completeChallengeCount = completeChallengeCount,
        couponCount = couponCount,
        nickname = nickname,
        profileImage = profileImage,
        status = status,
        xpPoint = xpPoint,
        title = title?.let { title ->
            Title(
                id = title.id,
                name = title.name,
                type = title.type,
                condition = title.condition,
                createdAt = title.createdAt
            )
        }
    )
}