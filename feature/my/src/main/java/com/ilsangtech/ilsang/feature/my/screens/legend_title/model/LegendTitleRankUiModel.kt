package com.ilsangtech.ilsang.feature.my.screens.legend_title.model

import com.ilsangtech.ilsang.core.model.title.Title

data class LegendTitleRankUiModel(
    val rank: Int,
    val nickname: String,
    val profileImageId: String?,
    val level: Int,
    val title: Title,
    val createdAt: String
)
