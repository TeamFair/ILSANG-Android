package com.ilsangtech.ilsang.core.data.quest.mapper

import com.ilsangtech.ilsang.core.model.QuestType
import com.ilsangtech.ilsang.core.model.quest.BannerQuest
import com.ilsangtech.ilsang.core.network.model.quest.BannerQuestNetworkModel
import com.ilsangtech.ilsang.core.network.model.quest.RewardPointNetworkModel

internal fun BannerQuestNetworkModel.toBannerQuest(): BannerQuest {
    return BannerQuest(
        questId = questId,
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
        expireDate = expireDate,
        imageId = imageId,
        mainImageId = mainImageId,
        rewards = rewards.map(RewardPointNetworkModel::toRewardPoint),
        title = title,
        writerName = writerName
    )
}