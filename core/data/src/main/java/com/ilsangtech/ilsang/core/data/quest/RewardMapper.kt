package com.ilsangtech.ilsang.core.data.quest

import com.ilsangtech.ilsang.core.model.Reward
import com.ilsangtech.ilsang.core.network.model.quest.RewardNetworkModel

fun RewardNetworkModel.toReward(): Reward {
    return Reward(
        content = content,
        discountRate = discountRate,
        quantity = quantity,
        questId = questId,
        rewardId = rewardId,
        target = target,
        title = title,
        type = type
    )
}