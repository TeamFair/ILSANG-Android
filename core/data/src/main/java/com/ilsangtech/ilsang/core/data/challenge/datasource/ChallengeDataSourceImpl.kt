package com.ilsangtech.ilsang.core.data.challenge.datasource

import com.ilsangtech.ilsang.core.network.api.ChallengeApiService
import com.ilsangtech.ilsang.core.network.model.challenge.ChallengeDeleteResponse
import com.ilsangtech.ilsang.core.network.model.challenge.ChallengeStatusUpdateResponse
import com.ilsangtech.ilsang.core.network.model.challenge.ChallengeSubmitRequest
import com.ilsangtech.ilsang.core.network.model.challenge.ChallengeSubmitResponse
import com.ilsangtech.ilsang.core.network.model.challenge.ChallengesResponse
import com.ilsangtech.ilsang.core.network.model.challenge.RandomChallengeResponse

class ChallengeDataSourceImpl(
    private val challengeApiService: ChallengeApiService
) : ChallengeDataSource {
    override suspend fun getChallenges(
        status: String,
        userId: String?,
        userDataOnly: Boolean,
        questId: String?,
        page: Int,
        size: Int
    ): ChallengesResponse {
        return challengeApiService.getChallenges(
            status = status,
            userId = userId,
            userDataOnly = userDataOnly,
            questId = questId,
            page = page,
            size = size
        )
    }

    override suspend fun submitChallenge(
        questId: String,
        imageId: String
    ): ChallengeSubmitResponse {
        return challengeApiService.submitChallenge(
            challengeSubmitRequest = ChallengeSubmitRequest(
                questId = questId,
                receiptImageId = imageId
            )
        )
    }

    override suspend fun getRandomChallenges(
        page: Int,
        size: Int
    ): RandomChallengeResponse {
        return challengeApiService.getRandomChallenges(
            page = page,
            size = size
        )
    }

    override suspend fun deleteChallenge(
        challengeId: String
    ): ChallengeDeleteResponse {
        return challengeApiService.deleteChallenge(
            challengeId = challengeId
        )
    }

    override suspend fun reportChallenge(
        challengeId: String
    ): ChallengeStatusUpdateResponse {
        return challengeApiService.updateChallengeStatus(
            challengeId = challengeId,
            status = "REPORTED"
        )
    }
}