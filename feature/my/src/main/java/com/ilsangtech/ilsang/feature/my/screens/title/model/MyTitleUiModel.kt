package com.ilsangtech.ilsang.feature.my.screens.title.model

import com.ilsangtech.ilsang.core.model.title.Title

data class MyTitleUiModel(
    val titleId: String,
    val titleHistoryId: Int?,
    val title: Title,
    val condition: String
)
