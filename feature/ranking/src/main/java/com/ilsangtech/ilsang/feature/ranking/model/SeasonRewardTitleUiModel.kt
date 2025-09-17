package com.ilsangtech.ilsang.feature.ranking.model

import com.ilsangtech.ilsang.core.model.title.SeasonRewardTitle
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

internal fun SeasonRewardTitle.toUiModel(): SeasonRewardTitleUiModel {
    val singleRankRegex = Regex("""(\d+)등""")
    val rangeRankRegex = Regex("""(\d+)~(\d+)등""")

    return when {
        rangeRankRegex.find(condition) != null -> {
            val match = rangeRankRegex.find(condition)!!
            val start = match.groupValues[1].toInt()
            val end = match.groupValues[2].toInt()
            SeasonRewardTitleUiModel.OtherRewardTitle(
                id = id,
                rankRange = start..end,
                title = title
            )
        }

        singleRankRegex.find(condition) != null -> {
            val match = singleRankRegex.find(condition)!!
            val rank = match.groupValues[1].toInt()
            SeasonRewardTitleUiModel.TopRewardTitle(
                id = id,
                rank = rank,
                title = title
            )
        }

        else -> throw IllegalArgumentException("Invalid condition: $condition")
    }
}