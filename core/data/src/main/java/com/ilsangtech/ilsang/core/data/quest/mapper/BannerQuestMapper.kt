package com.ilsangtech.ilsang.core.data.quest.mapper

import com.ilsangtech.ilsang.core.model.NewQuestType
import com.ilsangtech.ilsang.core.model.quest.BannerQuest
import com.ilsangtech.ilsang.core.network.model.quest.BannerQuestNetworkModel
import com.ilsangtech.ilsang.core.network.model.quest.RewardPointNetworkModel

internal fun BannerQuestNetworkModel.toBannerQuest(): BannerQuest {
    return BannerQuest(
        questId = questId,
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
        expireDate = expireDate,
        imageId = imageId,
        mainImageId = mainImageId,
        rewards = rewards.map(RewardPointNetworkModel::toRewardPoint),
        title = title,
        writerName = writerName
    )
}