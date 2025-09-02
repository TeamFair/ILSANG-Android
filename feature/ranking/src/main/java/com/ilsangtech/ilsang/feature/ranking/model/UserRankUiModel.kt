package com.ilsangtech.ilsang.feature.ranking.model

import com.ilsangtech.ilsang.core.model.rank.MyAreaRank
import com.ilsangtech.ilsang.core.model.rank.UserRank
import com.ilsangtech.ilsang.core.model.title.TitleGrade

data class UserRankUiModel(
    val userId: String,
    val profileImageId: String?,
    val nickname: String,
    val point: Int,
    val rank: Int?,
    val titleName: String?,
    val titleGrade: TitleGrade?
)

data class MyAreaRankUiModel(
    val userId: String,
    val profileImageId: String?,
    val nickname: String,
    val point: Int,
    val rank: Int?,
    val titleName: String?,
    val titleGrade: TitleGrade?,
    val pointGap: Int?
)

internal fun UserRank.toUserRankUiModel(): UserRankUiModel {
    return UserRankUiModel(
        userId = userId,
        profileImageId = profileImageId,
        nickname = nickName,
        point = point ?: 0,
        rank = rank,
        titleName = title?.name,
        titleGrade = title?.grade
    )
}

internal fun MyAreaRank.toMyAreaRankUiModel(): MyAreaRankUiModel {
    return MyAreaRankUiModel(
        userId = userId,
        profileImageId = profileImageId,
        nickname = nickName,
        point = point ?: 0,
        rank = rank,
        titleName = title?.name,
        titleGrade = title?.grade,
        pointGap = pointGap
    )
}