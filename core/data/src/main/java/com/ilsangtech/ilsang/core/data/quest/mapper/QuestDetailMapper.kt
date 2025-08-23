package com.ilsangtech.ilsang.core.data.quest.mapper

import com.ilsangtech.ilsang.core.data.mission.mapper.toMission
import com.ilsangtech.ilsang.core.model.NewQuestType
import com.ilsangtech.ilsang.core.model.quest.QuestDetail
import com.ilsangtech.ilsang.core.network.model.mission.MissionNetworkModel
import com.ilsangtech.ilsang.core.network.model.quest.QuestDetailResponse
import com.ilsangtech.ilsang.core.network.model.quest.RewardPointNetworkModel

internal fun QuestDetailResponse.toQuestDetail(): QuestDetail {
    return QuestDetail(
        id = id,
        expireDate = expireDate,
        favoriteYn = favoriteYn,
        imageId = imageId,
        mainImageId = mainImageId,
        missions = missions.map(MissionNetworkModel::toMission),
        questType = when (questType) {
            "NORMAL" -> NewQuestType.Normal
            "REPEAT" -> when (repeatFrequency) {
                "DAILY" -> NewQuestType.Repeat.Daily
                "WEEKLY" -> NewQuestType.Repeat.Weekly
                "MONTHLY" -> NewQuestType.Repeat.Monthly
                else -> throw IllegalArgumentException("Unknown repeat frequency: $repeatFrequency")
            }

            "EVENT" -> NewQuestType.Event
            else -> throw IllegalArgumentException("Unknown quest type: $questType")
        },
        rewards = rewards.map(RewardPointNetworkModel::toRewardPoint),
        title = title,
        userRank = userRank,
        writerName = writerName
    )
}