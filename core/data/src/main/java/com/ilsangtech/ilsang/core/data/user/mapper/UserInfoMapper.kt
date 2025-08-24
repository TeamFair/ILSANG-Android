package com.ilsangtech.ilsang.core.data.user.mapper

import com.ilsangtech.ilsang.core.data.title.mapper.toTitle
import com.ilsangtech.ilsang.core.model.UserInfo
import com.ilsangtech.ilsang.core.network.model.user.UserInfoResponse

internal fun UserInfoResponse.toUserInfo(): UserInfo {
    return UserInfo(
        id = id,
        channel = channel,
        commercialAreaCode = commercialAreaCode,
        email = email,
        nickname = nickname,
        profileImageId = profileImageId,
        status = status,
        statusUpdatedAt = statusUpdatedAt,
        title = title?.toTitle()
    )
}