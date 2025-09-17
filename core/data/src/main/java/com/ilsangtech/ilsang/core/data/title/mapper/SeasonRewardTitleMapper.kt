package com.ilsangtech.ilsang.core.data.title.mapper

import com.ilsangtech.ilsang.core.model.title.SeasonRewardTitle
import com.ilsangtech.ilsang.core.model.title.Title
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.core.model.title.TitleType
import com.ilsangtech.ilsang.core.network.model.title.SeasonRewardTitleNetworkModel

internal fun SeasonRewardTitleNetworkModel.toSeasonRewardTitle(): SeasonRewardTitle {
    return SeasonRewardTitle(
        id = id,
        title = Title(
            name = name,
            type = when (type) {
                "METRO" -> TitleType.Metro
                "COMMERCIAL" -> TitleType.Commercial
                "CONTRIBUTION" -> TitleType.Contribution
                "NONE" -> TitleType.None
                else -> throw IllegalArgumentException("Unknown title type: $type")
            },
            grade = when (grade) {
                "LEGEND" -> TitleGrade.Legend
                "RARE" -> TitleGrade.Rare
                "STANDARD" -> TitleGrade.Standard
                else -> throw IllegalArgumentException("Unknown title grade: $grade")
            }
        ),
        condition = condition
    )
}