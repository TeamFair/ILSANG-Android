package com.ilsangtech.ilsang.core.data.quest.mapper

import com.ilsangtech.ilsang.core.model.QuestType
import com.ilsangtech.ilsang.core.model.quest.PopularQuest
import com.ilsangtech.ilsang.core.network.model.quest.PopularQuestNetworkModel

internal fun PopularQuestNetworkModel.toPopularQuest(): PopularQuest {
    return PopularQuest(
        questId = questId,
        imageId = imageId,
        mainImageId = mainImageId,
        questType = when (questType) {
            "NORMAL" -> QuestType.Normal
            "REPEAT" -> {
                when (repeatFrequency) {
                    "DAILY" -> QuestType.Repeat.Daily
                    "WEEKLY" -> QuestType.Repeat.Weekly
                    "MONTHLY" -> QuestType.Repeat.Monthly
                    else -> throw IllegalArgumentException("Unknown repeat frequency: $repeatFrequency")
                }
            }

            "EVENT" -> QuestType.Event
            else -> throw IllegalArgumentException("Unknown quest type: $questType")
        },
        title = title,
        writerName = writerName,
        expireDate = expireDate
    )
}