package com.ilsangtech.ilsang.core.ui.season.model

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