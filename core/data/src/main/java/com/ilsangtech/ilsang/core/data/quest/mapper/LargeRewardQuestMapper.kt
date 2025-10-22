package com.ilsangtech.ilsang.core.data.quest.mapper

import com.ilsangtech.ilsang.core.model.quest.LargeRewardQuest
import com.ilsangtech.ilsang.core.network.model.quest.LargeRewardQuestNetworkModel
import com.ilsangtech.ilsang.core.network.model.quest.RewardPointNetworkModel

fun LargeRewardQuestNetworkModel.toLargeRewardQuest(isIsZoneQuest: Boolean): LargeRewardQuest {
    return LargeRewardQuest(
        questId = questId,
        expireDate = expireDate,
        imageId = imageId,
        mainImageId = mainImageId,
        rewards = rewards.map(RewardPointNetworkModel::toRewardPoint),
        title = title,
        writerName = writerName,
        isIsZoneQuest = isIsZoneQuest
    )
}