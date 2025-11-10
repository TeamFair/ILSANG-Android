package com.ilsangtech.ilsang.feature.profile.model

import com.ilsangtech.ilsang.core.model.title.Title
import com.ilsangtech.ilsang.core.model.user.UserInfo

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