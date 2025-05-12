package com.ilsangtech.ilsang.core.domain

import androidx.paging.PagingSource
import com.ilsangtech.ilsang.core.model.Challenge

interface ChallengeRepository {
    suspend fun getChallengesWithTotal(
        page: Int,
        size: Int
    ): Pair<List<Challenge>, Int>

    fun getChallengePaging(): PagingSource<Int, Challenge>

    suspend fun submitChallenge(
        imageBytes: ByteArray,
        questId: String
    ): String
}