package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.RewardType
import com.ilsangtech.ilsang.core.model.UserRank
import com.ilsangtech.ilsang.core.model.UserXpTypeRank

interface RankRepository {
    suspend fun getTopRankUsers(): List<UserRank>
    suspend fun getXpTypeRank(rewardType: RewardType): List<UserXpTypeRank>
}