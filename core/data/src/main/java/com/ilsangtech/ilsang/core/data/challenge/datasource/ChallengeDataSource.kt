package com.ilsangtech.ilsang.core.data.challenge.datasource

import com.ilsangtech.ilsang.core.network.model.challenge.ChallengeDeleteResponse
import com.ilsangtech.ilsang.core.network.model.challenge.ChallengeStatusUpdateResponse
import com.ilsangtech.ilsang.core.network.model.challenge.ChallengeSubmitResponse
import com.ilsangtech.ilsang.core.network.model.challenge.ChallengesResponse
import com.ilsangtech.ilsang.core.network.model.challenge.RandomChallengeResponse

interface ChallengeDataSource {
    suspend fun getChallenges(
        status: String,
        userId: String?,
        userDataOnly: Boolean,
        questId: String?,
        page: Int,
        size: Int
    ): ChallengesResponse

    suspend fun submitChallenge(
        questId: String,
        imageId: String
    ): ChallengeSubmitResponse

    suspend fun getRandomChallenges(
        page: Int,
        size: Int
    ): RandomChallengeResponse

    suspend fun deleteChallenge(
        challengeId: String
    ): ChallengeDeleteResponse

    suspend fun reportChallenge(
        challengeId: String
    ): ChallengeStatusUpdateResponse
}