package com.ilsangtech.ilsang.core.ui.user.model

import com.ilsangtech.ilsang.core.model.RewardPoint
import com.ilsangtech.ilsang.core.model.user.UserPoint
import com.ilsangtech.ilsang.core.ui.season.model.SeasonUiModel

data class UserObtainedPointUiModel(
    val completedQuestCount: Int,
    val metroAreaPoint: RewardPoint.Metro,
    val commercialAreaPoint: RewardPoint.Commercial,
    val contributionPoint: RewardPoint.Contribute,
    val seasonList: List<SeasonUiModel>
)

fun UserPoint.toUiModel(seasonList: List<SeasonUiModel>): UserObtainedPointUiModel {
    return UserObtainedPointUiModel(
        completedQuestCount = completedQuestCount,
        metroAreaPoint = RewardPoint.Metro(metroAreaPoint),
        commercialAreaPoint = RewardPoint.Commercial(commercialAreaPoint),
        contributionPoint = RewardPoint.Contribute(contributionPoint),
        seasonList = seasonList
    )
}