package com.ilsangtech.ilsang.core.data.rank.datasource

import com.ilsangtech.ilsang.core.network.api.RankApiService
import com.ilsangtech.ilsang.core.network.model.rank.TopUsersResponse
import javax.inject.Inject

class RankDataSourceImpl @Inject constructor(
    private val rankApiService: RankApiService
): RankDataSource {
    override suspend fun getTopRankUsers(): TopUsersResponse {
        return rankApiService.getTopRankUsers()
    }
}