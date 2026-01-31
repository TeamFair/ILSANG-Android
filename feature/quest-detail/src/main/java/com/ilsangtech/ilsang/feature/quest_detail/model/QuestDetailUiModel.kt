package com.ilsangtech.ilsang.feature.quest_detail.model

import com.ilsangtech.ilsang.core.model.coupon.QuestDetailCoupon
import com.ilsangtech.ilsang.core.model.mission.Mission
import com.ilsangtech.ilsang.core.model.quest.QuestDetail
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.model.reward.RewardPoint
import com.ilsangtech.ilsang.core.util.DateConverter

data class QuestDetailUiModel(
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
    val writerName: String,
    val isIsZoneQuest: Boolean = false,
    val remainHours: Int? = null
) {
    val isAvailable: Boolean
        get() = remainHours == null || remainHours <= 0
}

fun QuestDetail.toUiModel(remainHours: Int? = null): QuestDetailUiModel {
    return QuestDetailUiModel(
        id = id,
        expireDate = expireDate?.let {
            "${DateConverter.formatDate(it)} 까지"
        },
        favoriteYn = favoriteYn,
        imageId = imageId,
        mainImageId = mainImageId,
        missions = missions,
        questType = questType,
        rewards = rewards,
        coupons = coupons,
        title = title,
        userRank = userRank,
        writerName = writerName,
        isIsZoneQuest = isIsZoneQuest,
        remainHours = remainHours
    )
}