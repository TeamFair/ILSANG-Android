package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.UserRank

interface RankRepository {
    suspend fun getTopRankUsers(): List<UserRank>
}