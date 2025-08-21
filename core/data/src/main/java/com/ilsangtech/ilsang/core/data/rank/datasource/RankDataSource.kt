package com.ilsangtech.ilsang.core.data.rank.datasource

import com.ilsangtech.ilsang.core.network.model.rank.TopUsersResponse
import com.ilsangtech.ilsang.core.network.model.rank.UserRankNetworkModel
import com.ilsangtech.ilsang.core.network.model.rank.XpTypeRankResponse

interface RankDataSource {
    suspend fun getTotalTopRankUsers(commercialAreaCode: String): List<UserRankNetworkModel>

    suspend fun getTopRankUsers(): TopUsersResponse
    suspend fun getXpTypeRank(
        xpType: String,
        size: Int
    ): XpTypeRankResponse
}