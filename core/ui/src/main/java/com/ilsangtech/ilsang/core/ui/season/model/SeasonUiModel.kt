package com.ilsangtech.ilsang.core.ui.season.model

import com.ilsangtech.ilsang.core.model.season.Season

sealed interface SeasonUiModel {
    data object Total : SeasonUiModel {
        override fun toString(): String = "전체"
    }

    data class Specific(
        val id: Int,
        val seasonNumber: Int
    ) : SeasonUiModel {
        override fun toString(): String = "시즌 $seasonNumber"
    }
}

fun Season.toUiModel(): SeasonUiModel {
    return SeasonUiModel.Specific(
        id = id,
        seasonNumber = seasonNumber
    )
}