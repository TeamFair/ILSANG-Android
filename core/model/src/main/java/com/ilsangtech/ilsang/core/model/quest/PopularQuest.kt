package com.ilsangtech.ilsang.core.model.quest

import com.ilsangtech.ilsang.core.model.NewQuestType

data class PopularQuest(
    val questId: Int,
    val expireDate: String,
    val imageId: String,
    val mainImageId: String,
    val questType: NewQuestType,
    val title: String,
    val writerName: String
)