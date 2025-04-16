package com.ilsangtech.ilsang.core.data.challenge.datasource

import com.ilsangtech.ilsang.core.network.api.ChallengeApiService
import com.ilsangtech.ilsang.core.network.model.challenge.ChallengeNetworkModel

class ChallengeDataSourceImpl(
    private val challengeApiService: ChallengeApiService
) : ChallengeDataSource {
    override suspend fun getChallenges(
        authorization: String,
        status: String,
        userId: String,
        userDataOnly: Boolean,
        questId: String,
        page: Int,
        size: Int
    ): List<ChallengeNetworkModel> {
        return challengeApiService.getChallenges(
            authorization = authorization,
            status = status,
            userId = userId,
            userDataOnly = userDataOnly,
            questId = questId,
            page = page,
            size = size
        ).challengeList
    }
}