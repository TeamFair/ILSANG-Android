package com.ilsangtech.ilsang.core.model.quest

import com.ilsangtech.ilsang.core.model.reward.RewardPoint

data class BannerQuest(
    val questId: Int,
    val questType: QuestType,
    val expireDate: String,
    val imageId: String?,
    val mainImageId: String?,
    val rewards: List<RewardPoint>,
    val title: String,
    val writerName: String,
    val isIsZoneQuest: Boolean = false,
    val lastCompleteDate: String? = null
)