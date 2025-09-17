package com.ilsangtech.ilsang.feature.profile.model

import com.ilsangtech.ilsang.core.model.UserInfo
import com.ilsangtech.ilsang.core.model.title.Title

data class UserProfileInfoUiModel(
    val nickname: String,
    val profileImageId: String?,
    val title: Title?,
    val point: Int
)

internal fun UserInfo.toUserProfileInfoUiModel(point: Int): UserProfileInfoUiModel {
    return UserProfileInfoUiModel(
        nickname = nickname,
        profileImageId = profileImageId,
        title = userTitle?.title,
        point = point
    )
}