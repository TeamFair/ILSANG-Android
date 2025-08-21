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
            "Normal" -> NewQuestType.Normal
            "Repeat" -> {
                when (repeatFrequency) {
                    "Daily" -> NewQuestType.Repeat.Daily
                    "Weekly" -> NewQuestType.Repeat.Weekly
                    "Monthly" -> NewQuestType.Repeat.Monthly
                    else -> throw IllegalArgumentException("Unknown repeat frequency: $repeatFrequency")
                }
            }

            "Event" -> NewQuestType.Event
            else -> throw IllegalArgumentException("Unknown quest type: $questType")
        },
        title = title,
        writerName = writerName,
        expireDate = expireDate
    )
}