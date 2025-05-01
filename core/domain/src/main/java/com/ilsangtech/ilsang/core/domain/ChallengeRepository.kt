package com.ilsangtech.ilsang.core.domain

import androidx.paging.PagingSource
import com.ilsangtech.ilsang.core.model.Challenge
import com.ilsangtech.ilsang.core.model.RandomChallenge

interface ChallengeRepository {
    val randomChallengePagingSource: PagingSource<Int, RandomChallenge>

    suspend fun getChallengesWithTotal(
        page: Int,
        size: Int
    ): Pair<List<Challenge>, Int>

    suspend fun getRandomChallengesWithTotal(
        page: Int,
        size: Int
    ): Pair<List<RandomChallenge>, Int>

    fun getChallengePaging(): PagingSource<Int, Challenge>

    suspend fun submitChallenge(
        imageBytes: ByteArray,
        questId: String
    ): String
}