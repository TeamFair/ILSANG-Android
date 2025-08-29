package com.ilsangtech.ilsang.feature.my.screens.mytab.model

import com.ilsangtech.ilsang.core.model.season.Season
import com.ilsangtech.ilsang.core.model.user.UserPointSummary

data class MyPointSummaryUiModel(
    val nickName: String,
    val topCommercialAreaName: String,
    val topMetroAreaName: String,
    val topContributionPoint: Int,
    val seasonNumber: Int,
    val seasonStartDate: String,
    val seasonEndDate: String
)

internal suspend fun UserPointSummary.toMyPointSummaryUiModel(
    nickName: String,
    season: Season,
    changeAreaCodeToName: suspend (String) -> String
): MyPointSummaryUiModel {
    return MyPointSummaryUiModel(
        nickName = nickName,
        topCommercialAreaName = changeAreaCodeToName(topCommercialAreaCode),
        topMetroAreaName = changeAreaCodeToName(topMetroAreaCode),
        topContributionPoint = topContributionPoint,
        seasonNumber = season.seasonNumber,
        seasonStartDate = season.startDate,
        seasonEndDate = season.endDate
    )
}