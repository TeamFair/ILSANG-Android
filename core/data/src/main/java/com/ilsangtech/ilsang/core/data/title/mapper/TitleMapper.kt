package com.ilsangtech.ilsang.core.data.title.mapper

import com.ilsangtech.ilsang.core.model.title.Title
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.core.model.title.TitleType
import com.ilsangtech.ilsang.core.network.model.title.TitleNetworkModel

internal fun TitleNetworkModel.toTitle(): Title {
    return Title(
        name = name,
        grade = when (grade) {
            "STANDARD" -> TitleGrade.Standard
            "RARE" -> TitleGrade.Rare
            "LEGEND" -> TitleGrade.Legend
            else -> throw IllegalArgumentException("Unknown title grade: $grade")
        },
        type = when (type) {
            "METRO" -> TitleType.Metro
            "COMMERCIAL" -> TitleType.Commercial
            "CONTRIBUTION" -> TitleType.Contribution
            else -> throw IllegalArgumentException("Unknown title type: $type")
        }
    )
}