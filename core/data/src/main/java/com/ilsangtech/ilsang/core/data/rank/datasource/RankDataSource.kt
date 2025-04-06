package com.ilsangtech.ilsang.core.data.rank.datasource

import com.ilsangtech.ilsang.core.network.model.rank.TopUsersResponse

interface RankDataSource {
    suspend fun getTopRankUsers(): TopUsersResponse
}