package com.ilsangtech.ilsang.core.ui.quest.model

import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.model.quest.TypedQuest
import com.ilsangtech.ilsang.core.model.reward.RewardPoint
import com.ilsangtech.ilsang.core.util.DateConverter

data class TypedQuestUiModel(
    val questId: Int,
    val expireDate: String,
    val favoriteYn: Boolean,
    val imageId: String,
    val mainImageId: String?,
    val rewards: List<RewardPoint>,
    val title: String,
    val writerName: String,
    val questType: QuestType,
    val isIsZoneQuest: Boolean = false,
    val remainHours: Int? = null
) {
    val isAvailable: Boolean
        get() = remainHours == null || remainHours <= 0
}

fun TypedQuest.toUiModel(): TypedQuestUiModel {
    val questType = this.questType
    val lastCompleteDate = this.lastCompleteDate
    val remainHours = if (questType is QuestType.Repeat && lastCompleteDate != null) {
        when (questType) {
            is QuestType.Repeat.Daily -> DateConverter.getRemainHours(lastCompleteDate, day = 1)
            is QuestType.Repeat.Weekly -> DateConverter.getRemainHours(lastCompleteDate, week = 1)
            is QuestType.Repeat.Monthly -> DateConverter.getRemainHours(lastCompleteDate, month = 1)
        }
    } else {
        null
    }

    return TypedQuestUiModel(
        questId = questId,
        expireDate = expireDate,
        favoriteYn = favoriteYn,
        imageId = imageId,
        mainImageId = mainImageId,
        rewards = rewards,
        title = title,
        writerName = writerName,
        questType = questType,
        isIsZoneQuest = isIsZoneQuest,
        remainHours = remainHours
    )
}