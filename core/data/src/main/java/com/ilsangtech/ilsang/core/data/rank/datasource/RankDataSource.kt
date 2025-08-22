package com.ilsangtech.ilsang.core.data.rank.datasource

import com.ilsangtech.ilsang.core.network.model.rank.CommercialAreaRankNetworkModel
import com.ilsangtech.ilsang.core.network.model.rank.CommercialTopRankUsersResponse
import com.ilsangtech.ilsang.core.network.model.rank.MetroAreaRankNetworkModel
import com.ilsangtech.ilsang.core.network.model.rank.MetroTopRankUsersResponse
import com.ilsangtech.ilsang.core.network.model.rank.TopUsersResponse
import com.ilsangtech.ilsang.core.network.model.rank.UserRankNetworkModel
import com.ilsangtech.ilsang.core.network.model.rank.XpTypeRankResponse

interface RankDataSource {
    suspend fun getTotalTopRankUsers(commercialAreaCode: String): List<UserRankNetworkModel>

    suspend fun getMetroTopRankUsers(
        seasonId: Int?,
        metroAreaCode: String
    ): MetroTopRankUsersResponse

    suspend fun getCommercialTopRankUsers(
        seasonId: Int?,
        commercialAreaCode: String
    ): CommercialTopRankUsersResponse

    suspend fun getContributionTopRankUsers(
        seasonId: Int?
    ): List<UserRankNetworkModel>

    suspend fun getMetroTopRankAreas(seasonId: Int?): List<MetroAreaRankNetworkModel>

    suspend fun getCommercialTopRankAreas(seasonId: Int?): List<CommercialAreaRankNetworkModel>

    suspend fun getTopRankUsers(): TopUsersResponse
    suspend fun getXpTypeRank(
        xpType: String,
        size: Int
    ): XpTypeRankResponse
}