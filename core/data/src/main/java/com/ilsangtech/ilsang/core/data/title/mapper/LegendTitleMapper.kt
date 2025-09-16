package com.ilsangtech.ilsang.core.data.title.mapper

import com.ilsangtech.ilsang.core.model.title.LegendTitle
import com.ilsangtech.ilsang.core.model.title.Title
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.core.model.title.TitleType
import com.ilsangtech.ilsang.core.network.model.title.LegendTitleRankNetworkModel

internal fun LegendTitleRankNetworkModel.toLegendTitle(): LegendTitle {
    return LegendTitle(
        rank = rank,
        nickname = nickName,
        profileImageId = profileImageId,
        userPoint = point,
        title = Title(
            name = title.name,
            grade = when (title.grade) {
                "STANDARD" -> TitleGrade.Standard
                "RARE" -> TitleGrade.Rare
                "LEGEND" -> TitleGrade.Legend
                else -> throw IllegalArgumentException("Unknown title grade: ${title.grade}")
            },
            type = when (title.type) {
                "METRO" -> TitleType.Metro
                "COMMERCIAL" -> TitleType.Commercial
                "CONTRIBUTION" -> TitleType.Contribution
                "NONE" -> TitleType.None
                else -> throw IllegalArgumentException("Unknown title type: ${title.grade}")
            }
        ),
        createdAt = title.createdAt
    )
}