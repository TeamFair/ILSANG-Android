package com.ilsangtech.ilsang.core.model.quest

import com.ilsangtech.ilsang.core.model.NewQuestType
import com.ilsangtech.ilsang.core.model.RewardPoint

data class BannerQuest(
    val questId: Int,
    val questType: NewQuestType,
    val expireDate: String,
    val imageId: String?,
    val mainImageId: String?,
    val rewards: List<RewardPoint>,
    val title: String,
    val writerName: String
)