package com.ilsangtech.ilsang.feature.my.screens.legend_title.model

import com.ilsangtech.ilsang.core.model.title.LegendTitle
import com.ilsangtech.ilsang.core.model.title.Title
import com.ilsangtech.ilsang.core.util.DateConverter
import com.ilsangtech.ilsang.core.util.XpLevelCalculator

data class LegendTitleRankUiModel(
    val rank: Int,
    val nickname: String,
    val profileImageId: String?,
    val level: Int,
    val title: Title,
    val createdAt: String
)

internal fun LegendTitle.toUiModel(): LegendTitleRankUiModel {
    return LegendTitleRankUiModel(
        rank = rank,
        nickname = nickname,
        profileImageId = profileImageId,
        level = XpLevelCalculator.getCurrentLevel(userPoint),
        title = title,
        createdAt = DateConverter.formatDate(createdAt)
    )
}