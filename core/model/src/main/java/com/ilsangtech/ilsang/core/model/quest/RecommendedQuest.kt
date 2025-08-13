package com.ilsangtech.ilsang.core.model.quest

data class RecommendedQuest(
    val questId: Int,
    val imageId: String,
    val mainImageId: String,
    val title: String,
    val writerName: String
)