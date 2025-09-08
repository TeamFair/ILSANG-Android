package com.ilsangtech.ilsang.core.model.quest

import com.ilsangtech.ilsang.core.model.QuestType
import com.ilsangtech.ilsang.core.model.RewardPoint

data class TypedQuest(
    val questId: Int,
    val expireDate: String,
    val favoriteYn: Boolean,
    val imageId: String,
    val mainImageId: String?,
    val rewards: List<RewardPoint>,
    val title: String,
    val writerName: String,
    val questType: QuestType
)
