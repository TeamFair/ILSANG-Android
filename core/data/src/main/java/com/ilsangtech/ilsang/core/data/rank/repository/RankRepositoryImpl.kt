package com.ilsangtech.ilsang.core.data.rank.repository

import com.ilsangtech.ilsang.core.data.rank.datasource.RankDataSource
import com.ilsangtech.ilsang.core.data.rank.mapper.toUserRank
import com.ilsangtech.ilsang.core.data.rank.toUserXpTypeRank
import com.ilsangtech.ilsang.core.domain.RankRepository
import com.ilsangtech.ilsang.core.model.RewardType
import com.ilsangtech.ilsang.core.model.UserXpTypeRank
import com.ilsangtech.ilsang.core.model.rank.UserRank
import com.ilsangtech.ilsang.core.network.model.rank.UserRankNetworkModel
import com.ilsangtech.ilsang.core.network.model.rank.XpTypeRankNetworkModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RankRepositoryImpl @Inject constructor(
    private val rankDataSource: RankDataSource
) : RankRepository {
    override suspend fun getTotalTopRankUsers(commercialAreaCode: String): Flow<List<UserRank>> {
        return flow {
            emit(
                rankDataSource.getTotalTopRankUsers(commercialAreaCode)
                    .map(UserRankNetworkModel::toUserRank)
            )
        }
    }

    override suspend fun getTopRankUsers(): List<UserRank> {
        return rankDataSource.getTopRankUsers()
            .data.map(UserRankNetworkModel::toUserRank)
    }

    override suspend fun getXpTypeRank(rewardType: RewardType): List<UserXpTypeRank> {
        return rankDataSource.getXpTypeRank(
            xpType = rewardType.name,
            size = 20
        )
            .data.map(XpTypeRankNetworkModel::toUserXpTypeRank)
    }

}