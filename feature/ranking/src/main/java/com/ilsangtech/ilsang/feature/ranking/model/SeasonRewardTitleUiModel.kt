package com.ilsangtech.ilsang.feature.ranking.model

import com.ilsangtech.ilsang.core.model.title.Title

sealed class SeasonRewardTitleUiModel(
    open val title: Title
) {
    data class TopRewardTitle(
        val rank: Int,
        override val title: Title
    ) : SeasonRewardTitleUiModel(title)

    data class OtherRewardTitle(
        val rankRange: IntRange,
        override val title: Title
    ) : SeasonRewardTitleUiModel(title)
}
