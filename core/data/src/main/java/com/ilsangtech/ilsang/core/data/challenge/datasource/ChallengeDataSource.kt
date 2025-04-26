package com.ilsangtech.ilsang.core.data.challenge.datasource

import com.ilsangtech.ilsang.core.network.model.challenge.ChallengesResponse

interface ChallengeDataSource {
    suspend fun getChallenges(
        authorization: String,
        status: String,
        userId: String?,
        userDataOnly: Boolean,
        questId: String?,
        page: Int,
        size: Int
    ): ChallengesResponse
}