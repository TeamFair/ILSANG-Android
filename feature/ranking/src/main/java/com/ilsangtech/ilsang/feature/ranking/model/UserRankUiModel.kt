package com.ilsangtech.ilsang.feature.ranking.model

import com.ilsangtech.ilsang.core.model.rank.UserRank
import com.ilsangtech.ilsang.core.model.title.TitleGrade

data class UserRankUiModel(
    val userId: String,
    val profileImageId: String?,
    val nickname: String,
    val point: Int,
    val rank: Int,
    val titleName: String,
    val titleGrade: TitleGrade
)

internal fun UserRank.toUserRankUiModel(): UserRankUiModel {
    return UserRankUiModel(
        userId = userId,
        profileImageId = profileImageId,
        nickname = nickName,
        point = point,
        rank = rank,
        titleName = title.name,
        titleGrade = title.grade
    )
}