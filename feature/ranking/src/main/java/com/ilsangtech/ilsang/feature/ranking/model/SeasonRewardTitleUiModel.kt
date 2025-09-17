package com.ilsangtech.ilsang.feature.ranking.model

import com.ilsangtech.ilsang.core.model.title.Title

sealed class SeasonRewardTitleUiModel(
    open val id: String,
    open val title: Title
) {
    data class TopRewardTitle(
        override val id: String,
        val rank: Int,
        override val title: Title
    ) : SeasonRewardTitleUiModel(id, title)

    data class OtherRewardTitle(
        override val id: String,
        val rankRange: IntRange,
        override val title: Title
    ) : SeasonRewardTitleUiModel(id, title)
}
