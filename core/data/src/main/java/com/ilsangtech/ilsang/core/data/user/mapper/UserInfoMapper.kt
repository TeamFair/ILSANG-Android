package com.ilsangtech.ilsang.core.data.user.mapper

import com.ilsangtech.ilsang.core.data.title.mapper.toUserTitle
import com.ilsangtech.ilsang.core.model.user.UserInfo
import com.ilsangtech.ilsang.core.network.model.user.UserResponse

internal fun UserResponse.toUserInfo(): UserInfo {
    return UserInfo(
        id = id,
        channel = channel,
        commercialAreaCode = commercialAreaCode,
        email = email,
        nickname = nickname,
        profileImageId = profileImageId,
        status = status,
        statusUpdatedAt = statusUpdatedAt,
        userTitle = title?.toUserTitle()
    )
}