package com.ilsangtech.ilsang.feature.ranking.model

import com.ilsangtech.ilsang.core.model.season.Season

sealed interface SeasonUiModel {
    data object Total : SeasonUiModel {
        override fun toString(): String {
            return "전체"
        }
    }

    data class Season(
        val seasonId: Int,
        val seasonNumber: Int,
        val startDate: String,
        val endDate: String
    ) : SeasonUiModel {
        override fun toString(): String {
            return "시즌 $seasonNumber"
        }
    }
}

internal fun Season.toSeasonUiModel(): SeasonUiModel.Season {
    return SeasonUiModel.Season(
        seasonId = id,
        seasonNumber = seasonNumber,
        startDate = startDate,
        endDate = endDate
    )
}