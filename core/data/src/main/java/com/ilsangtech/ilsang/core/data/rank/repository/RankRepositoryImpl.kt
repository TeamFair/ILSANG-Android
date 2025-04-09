package com.ilsangtech.ilsang.core.data.rank.repository

import com.ilsangtech.ilsang.core.data.rank.datasource.RankDataSource
import com.ilsangtech.ilsang.core.data.rank.toUserRank
import com.ilsangtech.ilsang.core.domain.RankRepository
import com.ilsangtech.ilsang.core.model.UserRank
import com.ilsangtech.ilsang.core.network.model.rank.UserRankNetworkModel
import javax.inject.Inject

class RankRepositoryImpl @Inject constructor(
    private val rankDataSource: RankDataSource
) : RankRepository {
    override suspend fun getTopRankUsers(): List<UserRank> {
        return rankDataSource.getTopRankUsers()
            .data.map(UserRankNetworkModel::toUserRank)
    }
}