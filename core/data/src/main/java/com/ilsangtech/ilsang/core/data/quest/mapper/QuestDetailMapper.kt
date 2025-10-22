package com.ilsangtech.ilsang.core.data.quest.mapper

import com.ilsangtech.ilsang.core.data.coupon.mapper.toQuestDetailCoupon
import com.ilsangtech.ilsang.core.data.mission.mapper.toMission
import com.ilsangtech.ilsang.core.model.quest.QuestDetail
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.network.model.coupon.QuestDetailCouponNetworkModel
import com.ilsangtech.ilsang.core.network.model.mission.MissionNetworkModel
import com.ilsangtech.ilsang.core.network.model.quest.QuestDetailResponse
import com.ilsangtech.ilsang.core.network.model.quest.RewardPointNetworkModel

internal fun QuestDetailResponse.toQuestDetail(isIsZoneQuest: Boolean): QuestDetail {
    return QuestDetail(
        id = id,
        expireDate = expireDate,
        favoriteYn = favoriteYn,
        imageId = imageId,
        mainImageId = mainImageId,
        missions = missions.map(MissionNetworkModel::toMission),
        questType = when (questType) {
            "NORMAL" -> QuestType.Normal
            "REPEAT" -> when (repeatFrequency) {
                "DAILY" -> QuestType.Repeat.Daily
                "WEEKLY" -> QuestType.Repeat.Weekly
                "MONTHLY" -> QuestType.Repeat.Monthly
                else -> throw IllegalArgumentException("Unknown repeat frequency: $repeatFrequency")
            }

            "EVENT" -> QuestType.Event
            else -> throw IllegalArgumentException("Unknown quest type: $questType")
        },
        rewards = rewards.map(RewardPointNetworkModel::toRewardPoint),
        coupons = coupons.map(QuestDetailCouponNetworkModel::toQuestDetailCoupon),
        title = title,
        userRank = userRank,
        writerName = writerName,
        isIsZoneQuest = isIsZoneQuest
    )
}