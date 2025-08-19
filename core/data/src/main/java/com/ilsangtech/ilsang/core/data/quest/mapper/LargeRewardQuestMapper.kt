package com.ilsangtech.ilsang.core.data.quest.mapper

import com.ilsangtech.ilsang.core.model.quest.LargeRewardQuest
import com.ilsangtech.ilsang.core.network.model.quest.LargeRewardQuestNetworkModel
import com.ilsangtech.ilsang.core.network.model.quest.RewardPointNetworkModel

fun LargeRewardQuestNetworkModel.toLargeRewardQuest(): LargeRewardQuest {
    return LargeRewardQuest(
        questId = questId,
        expireDate = expireDate,
        imageId = imageId,
        mainImageId = mainImageId,
        rewards = rewardPointNetworkModels.map(RewardPointNetworkModel::toRewardPoint),
        title = title,
        writerName = writerName
    )
}