package com.ilsangtech.ilsang.core.model.quest

import com.ilsangtech.ilsang.core.model.RewardPoint

data class LargeRewardQuest(
    val questId: Int,
    val expireDate: String,
    val imageId: String,
    val mainImageId: String,
    val rewards: List<RewardPoint>,
    val title: String,
    val writerName: String
)