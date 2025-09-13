package com.ilsangtech.ilsang.core.model.quest

import com.ilsangtech.ilsang.core.model.coupon.QuestDetailCoupon
import com.ilsangtech.ilsang.core.model.mission.Mission
import com.ilsangtech.ilsang.core.model.reward.RewardPoint

data class QuestDetail(
    val id: Int,
    val expireDate: String?,
    val favoriteYn: Boolean,
    val imageId: String?,
    val mainImageId: String?,
    val missions: List<Mission>,
    val questType: QuestType,
    val rewards: List<RewardPoint>,
    val coupons: List<QuestDetailCoupon>,
    val title: String,
    val userRank: Int?,
    val writerName: String
)