package com.ilsangtech.ilsang.core.model.quest

import com.ilsangtech.ilsang.core.model.RewardPoint
import com.ilsangtech.ilsang.core.model.mission.Mission

data class QuestDetail(
    val id: Int,
    val expireDate: String?,
    val favoriteYn: Boolean,
    val imageId: String?,
    val mainImageId: String?,
    val missions: List<Mission>,
    val questType: QuestType,
    val rewards: List<RewardPoint>,
    val title: String,
    val userRank: Int?,
    val writerName: String
)