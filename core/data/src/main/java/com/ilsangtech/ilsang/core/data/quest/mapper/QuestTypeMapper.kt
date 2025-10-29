package com.ilsangtech.ilsang.core.data.quest.mapper

import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.model.quest.QuestType.Event
import com.ilsangtech.ilsang.core.model.quest.QuestType.Normal
import com.ilsangtech.ilsang.core.model.quest.QuestType.Repeat

internal fun QuestType.Companion.fromString(type: String, repeatFrequency: String?): QuestType {
    return when (type) {
        "NORMAL" -> Normal
        "REPEAT" -> when (repeatFrequency) {
            "DAILY" -> Repeat.Daily
            "WEEKLY" -> Repeat.Weekly
            "MONTHLY" -> Repeat.Monthly
            else -> throw IllegalArgumentException("Unknown repeat frequency: $repeatFrequency")
        }

        "EVENT" -> Event
        else -> throw IllegalArgumentException("Unknown quest type: $type")
    }
}