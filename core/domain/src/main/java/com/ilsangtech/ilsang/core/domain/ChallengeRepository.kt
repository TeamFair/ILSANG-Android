package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.Challenge

interface ChallengeRepository {
    suspend fun getChallenges(
        page: Int,
        size: Int
    ): List<Challenge>
}