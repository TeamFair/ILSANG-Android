package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.RewardType
import com.ilsangtech.ilsang.core.model.UserXpTypeRank
import com.ilsangtech.ilsang.core.model.rank.CommercialAreaRank
import com.ilsangtech.ilsang.core.model.rank.MetroAreaRank
import com.ilsangtech.ilsang.core.model.rank.UserRank
import com.ilsangtech.ilsang.core.model.rank.UserRanksWithMyRank
import kotlinx.coroutines.flow.Flow

interface RankRepository {
    fun getTotalTopRankUsers(commercialAreaCode: String): Flow<List<UserRank>>

    fun getMetroTopRankUsers(
        seasonId: Int?,
        metroAreaCode: String
    ): Flow<UserRanksWithMyRank>

    fun getCommercialTopRankUsers(
        seasonId: Int?,
        commercialAreaCode: String
    ): Flow<UserRanksWithMyRank>

    fun getContributionTopRankUsers(
        seasonId: Int?
    ): Flow<List<UserRank>>

    fun getMetroTopRankAreas(
        seasonId: Int?
    ): Flow<List<MetroAreaRank>>

    fun getCommercialTopRankAreas(
        seasonId: Int?
    ): Flow<List<CommercialAreaRank>>

    suspend fun getTopRankUsers(): List<UserRank>
    suspend fun getXpTypeRank(rewardType: RewardType): List<UserXpTypeRank>
}