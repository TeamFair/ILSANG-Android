package com.ilsangtech.ilsang.core.data.challenge.datasource

import com.ilsangtech.ilsang.core.network.api.ChallengeApiService
import com.ilsangtech.ilsang.core.network.model.challenge.ChallengeDeleteResponse
import com.ilsangtech.ilsang.core.network.model.challenge.ChallengeSubmitRequest
import com.ilsangtech.ilsang.core.network.model.challenge.ChallengeSubmitResponse
import com.ilsangtech.ilsang.core.network.model.challenge.ChallengesResponse
import com.ilsangtech.ilsang.core.network.model.challenge.RandomChallengeResponse

class ChallengeDataSourceImpl(
    private val challengeApiService: ChallengeApiService
) : ChallengeDataSource {
    override suspend fun getChallenges(
        authorization: String,
        status: String,
        userId: String?,
        userDataOnly: Boolean,
        questId: String?,
        page: Int,
        size: Int
    ): ChallengesResponse {
        return challengeApiService.getChallenges(
            authorization = authorization,
            status = status,
            userId = userId,
            userDataOnly = userDataOnly,
            questId = questId,
            page = page,
            size = size
        )
    }

    override suspend fun submitChallenge(
        authorization: String,
        questId: String,
        imageId: String
    ): ChallengeSubmitResponse {
        return challengeApiService.submitChallenge(
            authorization = authorization,
            challengeSubmitRequest = ChallengeSubmitRequest(
                questId = questId,
                receiptImageId = imageId
            )
        )
    }

    override suspend fun getRandomChallenges(
        authorization: String,
        page: Int,
        size: Int
    ): RandomChallengeResponse {
        return challengeApiService.getRandomChallenges(
            authorization = authorization,
            page = page,
            size = size
        )
    }

    override suspend fun deleteChallenge(
        authorization: String,
        challengeId: String
    ): ChallengeDeleteResponse {
        return challengeApiService.deleteChallenge(
            authorization = authorization,
            challengeId = challengeId
        )
    }
}