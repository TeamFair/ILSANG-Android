package com.ilsangtech.ilsang.feature.my.screens.mytab.model

import com.ilsangtech.ilsang.core.model.title.Title

data class MyProfileInfoUiModel(
    val nickname: String,
    val profileImageId: String?,
    val levelProgress: Float,
    val level: Int,
    val title: Title?
)
