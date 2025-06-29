package com.ilsangtech.ilsang.core.domain

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.ilsangtech.ilsang.core.model.Challenge
import com.ilsangtech.ilsang.core.model.RandomChallenge
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    val randomChallengePagingSource: PagingSource<Int, RandomChallenge>

    suspend fun getRandomChallengesWithTotal(
        page: Int,
        size: Int
    ): Pair<List<RandomChallenge>, Int>

    fun getChallengePaging(userId: String? = null): Flow<PagingData<Challenge>>

    suspend fun submitChallenge(
        imageBytes: ByteArray,
        questId: String
    ): String

    suspend fun deleteChallenge(challengeId: String): Result<Unit>

    suspend fun reportChallenge(challengeId: String): Result<Unit>
}