package com.ilsangtech.ilsang.feature.home.model

import com.ilsangtech.ilsang.core.model.season.Season
import com.ilsangtech.ilsang.core.util.DateConverter

data class OpenSeasonUiModel(
    val seasonNumber: Int,
    val startDate: String,
    val endDate: String
)

internal fun Season.toOpenSeasonUiModel(): OpenSeasonUiModel {
    return OpenSeasonUiModel(
        seasonNumber = seasonNumber,
        startDate = DateConverter.formatDate(startDate),
        endDate = DateConverter.formatDate(endDate)
    )
}
