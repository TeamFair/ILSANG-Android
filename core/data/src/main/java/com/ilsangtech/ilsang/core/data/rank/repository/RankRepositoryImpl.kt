package com.ilsangtech.ilsang.core.data.rank.repository

import com.ilsangtech.ilsang.core.data.rank.datasource.RankDataSource
import com.ilsangtech.ilsang.core.data.rank.mapper.toCommercialAreaRank
import com.ilsangtech.ilsang.core.data.rank.mapper.toMetroAreaRank
import com.ilsangtech.ilsang.core.data.rank.mapper.toUserRank
import com.ilsangtech.ilsang.core.data.rank.mapper.toUserRanksWithMyRank
import com.ilsangtech.ilsang.core.domain.RankRepository
import com.ilsangtech.ilsang.core.model.rank.CommercialAreaRank
import com.ilsangtech.ilsang.core.model.rank.MetroAreaRank
import com.ilsangtech.ilsang.core.model.rank.UserRank
import com.ilsangtech.ilsang.core.model.rank.UserRanksWithMyRank
import com.ilsangtech.ilsang.core.network.model.rank.CommercialAreaRankNetworkModel
import com.ilsangtech.ilsang.core.network.model.rank.MetroAreaRankNetworkModel
import com.ilsangtech.ilsang.core.network.model.rank.UserRankNetworkModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RankRepositoryImpl @Inject constructor(
    private val rankDataSource: RankDataSource
) : RankRepository {
    override fun getTotalTopRankUsers(commercialAreaCode: String): Flow<List<UserRank>> {
        return flow {
            emit(
                rankDataSource.getTotalTopRankUsers(commercialAreaCode)
                    .map(UserRankNetworkModel::toUserRank)
            )
        }
    }

    override fun getMetroTopRankUsers(
        seasonId: Int?,
        metroAreaCode: String
    ): Flow<UserRanksWithMyRank> {
        return flow {
            emit(
                rankDataSource.getMetroTopRankUsers(seasonId, metroAreaCode)
                    .toUserRanksWithMyRank()
            )
        }
    }

    override fun getCommercialTopRankUsers(
        seasonId: Int?,
        commercialAreaCode: String
    ): Flow<UserRanksWithMyRank> {
        return flow {
            emit(
                rankDataSource.getCommercialTopRankUsers(seasonId, commercialAreaCode)
                    .toUserRanksWithMyRank()
            )
        }
    }

    override fun getContributionTopRankUsers(seasonId: Int?): Flow<List<UserRank>> {
        return flow {
            emit(
                rankDataSource.getContributionTopRankUsers(seasonId)
                    .map(UserRankNetworkModel::toUserRank)
            )
        }
    }

    override fun getMetroTopRankAreas(seasonId: Int?): Flow<List<MetroAreaRank>> {
        return flow {
            emit(
                rankDataSource.getMetroTopRankAreas(seasonId)
                    .map(MetroAreaRankNetworkModel::toMetroAreaRank)
            )
        }
    }

    override fun getCommercialTopRankAreas(seasonId: Int?): Flow<List<CommercialAreaRank>> {
        return flow {
            emit(
                rankDataSource.getCommercialTopRankAreas(seasonId)
                    .map(CommercialAreaRankNetworkModel::toCommercialAreaRank)
            )
        }
    }
}