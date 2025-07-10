package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.challenge.ChallengeDeleteResponse
import com.ilsangtech.ilsang.core.network.model.challenge.ChallengeStatusUpdateResponse
import com.ilsangtech.ilsang.core.network.model.challenge.ChallengeSubmitRequest
import com.ilsangtech.ilsang.core.network.model.challenge.ChallengeSubmitResponse
import com.ilsangtech.ilsang.core.network.model.challenge.ChallengesResponse
import com.ilsangtech.ilsang.core.network.model.challenge.RandomChallengeResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ChallengeApiService {
    @GET("customer/challenge")
    suspend fun getChallenges(
        @Query("status") status: String,
        @Query("userId") userId: String?,
        @Query("userDataOnly") userDataOnly: Boolean,
        @Query("questId") questId: String?,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ChallengesResponse

    @GET("customer/randomChallenge")
    suspend fun getRandomChallenges(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): RandomChallengeResponse

    @POST("customer/challenge")
    suspend fun submitChallenge(
        @Body challengeSubmitRequest: ChallengeSubmitRequest
    ): ChallengeSubmitResponse

    @DELETE("customer/{challengeId}")
    suspend fun deleteChallenge(
        @Path("challengeId") challengeId: String
    ): ChallengeDeleteResponse

    @PATCH("customer/status")
    suspend fun updateChallengeStatus(
        @Query("challengeId") challengeId: String,
        @Query("status") status: String
    ): ChallengeStatusUpdateResponse
}