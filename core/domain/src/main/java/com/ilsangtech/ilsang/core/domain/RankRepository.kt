package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.RewardType
import com.ilsangtech.ilsang.core.model.UserXpTypeRank
import com.ilsangtech.ilsang.core.model.rank.UserRank
import kotlinx.coroutines.flow.Flow

interface RankRepository {
    suspend fun getTotalTopRankUsers(commercialAreaCode: String): Flow<List<UserRank>>
    suspend fun getTopRankUsers(): List<UserRank>
    suspend fun getXpTypeRank(rewardType: RewardType): List<UserXpTypeRank>
}