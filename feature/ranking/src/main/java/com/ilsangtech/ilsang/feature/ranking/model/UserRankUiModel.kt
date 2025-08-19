package com.ilsangtech.ilsang.feature.ranking.model

import com.ilsangtech.ilsang.core.model.title.TitleGrade

data class UserRankUiModel(
    val userId: String,
    val profileImageId: String,
    val nickname: String,
    val point: Int,
    val rank: Int,
    val titleName: String,
    val titleGrade: TitleGrade
)
