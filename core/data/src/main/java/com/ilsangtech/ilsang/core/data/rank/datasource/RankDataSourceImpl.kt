package com.ilsangtech.ilsang.core.data.rank.datasource

import com.ilsangtech.ilsang.core.network.api.RankApiService
import com.ilsangtech.ilsang.core.network.model.rank.CommercialAreaRankNetworkModel
import com.ilsangtech.ilsang.core.network.model.rank.CommercialTopRankUsersResponse
import com.ilsangtech.ilsang.core.network.model.rank.ContributionTopRankUsersResponse
import com.ilsangtech.ilsang.core.network.model.rank.MetroAreaRankNetworkModel
import com.ilsangtech.ilsang.core.network.model.rank.MetroTopRankUsersResponse
import com.ilsangtech.ilsang.core.network.model.rank.TopUsersResponse
import com.ilsangtech.ilsang.core.network.model.rank.UserRankNetworkModel
import com.ilsangtech.ilsang.core.network.model.rank.XpTypeRankResponse
import javax.inject.Inject

class RankDataSourceImpl @Inject constructor(
    private val rankApiService: RankApiService
) : RankDataSource {
    override suspend fun getTotalTopRankUsers(commercialAreaCode: String): List<UserRankNetworkModel> {
        return rankApiService.getTotalTopRankUsers(commercialAreaCode)
    }

    override suspend fun getMetroTopRankUsers(
        seasonId: Int?,
        metroAreaCode: String
    ): MetroTopRankUsersResponse {
        return rankApiService.getMetroTopRankUsers(seasonId, metroAreaCode)
    }

    override suspend fun getCommercialTopRankUsers(
        seasonId: Int?,
        commercialAreaCode: String
    ): CommercialTopRankUsersResponse {
        return rankApiService.getCommercialTopRankUsers(seasonId, commercialAreaCode)
    }

    override suspend fun getContributionTopRankUsers(
        seasonId: Int?
    ): ContributionTopRankUsersResponse {
        return rankApiService.getContributionTopRankUsers(seasonId)
    }

    override suspend fun getMetroTopRankAreas(
        seasonId: Int?
    ): List<MetroAreaRankNetworkModel> {
        return rankApiService.getMetroTopRankAreas(seasonId)
    }

    override suspend fun getCommercialTopRankAreas(
        seasonId: Int?
    ): List<CommercialAreaRankNetworkModel> {
        return rankApiService.getCommercialTopRankAreas(seasonId)
    }

    override suspend fun getTopRankUsers(): TopUsersResponse {
        return rankApiService.getTopRankUsers()
    }

    override suspend fun getXpTypeRank(
        xpType: String,
        size: Int
    ): XpTypeRankResponse {
        return rankApiService.getXpTypeRank(xpType, size)
    }
}