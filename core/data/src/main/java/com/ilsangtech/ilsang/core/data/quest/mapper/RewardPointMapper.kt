package com.ilsangtech.ilsang.core.data.quest.mapper

import com.ilsangtech.ilsang.core.model.RewardPoint
import com.ilsangtech.ilsang.core.network.model.quest.RewardPointNetworkModel

fun RewardPointNetworkModel.toRewardPoint(): RewardPoint {
    return when (pointType) {
        "METRO" -> RewardPoint.Metro(point)
        "COMMERCIAL" -> RewardPoint.Commercial(point)
        "CONTRIBUTE" -> RewardPoint.Contribute(point)
        else -> throw IllegalArgumentException("Unknown point type: $pointType")
    }
}