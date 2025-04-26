package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.challenge.ChallengeSubmitRequest
import com.ilsangtech.ilsang.core.network.model.challenge.ChallengeSubmitResponse
import com.ilsangtech.ilsang.core.network.model.challenge.ChallengesResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ChallengeApiService {
    @GET("customer/challenge")
    suspend fun getChallenges(
        @Header("authorization") authorization: String,
        @Query("status") status: String,
        @Query("userId") userId: String?,
        @Query("userDataOnly") userDataOnly: Boolean,
        @Query("questId") questId: String?,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ChallengesResponse

    @POST("customer/challenge")
    suspend fun submitChallenge(
        @Header("authorization") authorization: String,
        @Body challengeSubmitRequest: ChallengeSubmitRequest
    ): ChallengeSubmitResponse
}