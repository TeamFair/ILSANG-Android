package com.ilsangtech.ilsang.feature.ranking.model

sealed interface SeasonUiModel {
    data object Total : SeasonUiModel {
        override fun toString(): String {
            return "전체"
        }
    }

    data class Season(
        val seasonNumber: Int,
        val startDate: String,
        val endDate: String
    ) : SeasonUiModel {
        override fun toString(): String {
            return "시즌 $seasonNumber"
        }
    }
}