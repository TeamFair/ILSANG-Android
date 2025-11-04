package com.ilsangtech.ilsang.core.ui.quest.model

import com.ilsangtech.ilsang.core.model.quest.BannerQuest
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.model.reward.RewardPoint
import com.ilsangtech.ilsang.core.util.DateConverter

data class BannerQuestUiModel(
    val questId: Int,
    val questType: QuestType,
    val expireDate: String,
    val imageId: String?,
    val mainImageId: String?,
    val rewards: List<RewardPoint>,
    val title: String,
    val writerName: String,
    val isIsZoneQuest: Boolean = false,
    val remainHours: Int? = null
) {
    val isAvailable: Boolean
        get() = remainHours == null || remainHours <= 0
}

fun BannerQuest.toUiModel(): BannerQuestUiModel {
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

    return BannerQuestUiModel(
        questId = questId,
        questType = questType,
        expireDate = expireDate,
        imageId = imageId,
        mainImageId = mainImageId,
        rewards = rewards,
        title = title,
        writerName = writerName,
        isIsZoneQuest = isIsZoneQuest,
        remainHours = remainHours
    )
}