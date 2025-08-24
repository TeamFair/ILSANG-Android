package com.ilsangtech.ilsang.core.data.quest.mapper

import com.ilsangtech.ilsang.core.model.NewQuestType
import com.ilsangtech.ilsang.core.model.quest.PopularQuest
import com.ilsangtech.ilsang.core.network.model.quest.PopularQuestNetworkModel

internal fun PopularQuestNetworkModel.toPopularQuest(): PopularQuest {
    return PopularQuest(
        questId = questId,
        imageId = imageId,
        mainImageId = mainImageId,
        questType = when (questType) {
            "NORMAL" -> NewQuestType.Normal
            "REPEAT" -> {
                when (repeatFrequency) {
                    "DAILY" -> NewQuestType.Repeat.Daily
                    "WEEKLY" -> NewQuestType.Repeat.Weekly
                    "MONTHLY" -> NewQuestType.Repeat.Monthly
                    else -> throw IllegalArgumentException("Unknown repeat frequency: $repeatFrequency")
                }
            }

            "EVENT" -> NewQuestType.Event
            else -> throw IllegalArgumentException("Unknown quest type: $questType")
        },
        title = title,
        writerName = writerName,
        expireDate = expireDate
    )
}