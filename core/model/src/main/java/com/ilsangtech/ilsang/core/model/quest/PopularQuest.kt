package com.ilsangtech.ilsang.core.model.quest

data class PopularQuest(
    val questId: Int,
    val expireDate: String,
    val imageId: String?,
    val mainImageId: String?,
    val questType: QuestType,
    val title: String,
    val writerName: String
)