package com.ilsangtech.ilsang.core.model.quest

import com.ilsangtech.ilsang.core.model.RewardPoint

//TODO NewQuest -> Quest로 이름 변경, 패키지 이동
data class NewQuest(
    val questId: Int,
    val expireDate: String,
    val favoriteYn: Boolean,
    val imageId: String,
    val mainImageId: String,
    val rewards: List<RewardPoint>,
    val title: String,
    val writerName: String
)