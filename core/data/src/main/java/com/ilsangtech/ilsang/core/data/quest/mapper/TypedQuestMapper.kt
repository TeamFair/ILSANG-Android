package com.ilsangtech.ilsang.core.data.quest.mapper

import com.ilsangtech.ilsang.core.model.QuestType
import com.ilsangtech.ilsang.core.model.quest.TypedQuest
import com.ilsangtech.ilsang.core.network.model.quest.RewardPointNetworkModel
import com.ilsangtech.ilsang.core.network.model.quest.TypedQuestNetworkModel

internal fun TypedQuestNetworkModel.toTypedQuest(): TypedQuest {
    return TypedQuest(
        questId = questId,
        expireDate = expireDate,
        favoriteYn = favoriteYn,
        imageId = imageId,
        mainImageId = mainImageId,
        rewards = rewards.map(RewardPointNetworkModel::toRewardPoint),
        title = title,
        writerName = writerName,
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
        }
    )
}