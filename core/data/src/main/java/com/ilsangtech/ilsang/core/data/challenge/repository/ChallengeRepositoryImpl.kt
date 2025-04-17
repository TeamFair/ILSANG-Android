package com.ilsangtech.ilsang.core.data.challenge.repository

import com.ilsangtech.ilsang.core.data.challenge.datasource.ChallengeDataSource
import com.ilsangtech.ilsang.core.data.challenge.toChallenge
import com.ilsangtech.ilsang.core.domain.ChallengeRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.Challenge
import com.ilsangtech.ilsang.core.network.model.challenge.ChallengeNetworkModel

class ChallengeRepositoryImpl(
    private val userRepository: UserRepository,
    private val challengeDataSource: ChallengeDataSource
) : ChallengeRepository {
    override suspend fun getChallengesWithTotal(
        page: Int,
        size: Int
    ): Pair<List<Challenge>, Int> {
        val response = challengeDataSource.getChallenges(
            authorization = userRepository.currentUser?.authorization!!,
            status = "APPROVED",
            userId = "",
            userDataOnly = true,
            questId = "",
            page = page,
            size = size
        )
        return response.challengeList.map(ChallengeNetworkModel::toChallenge) to response.total
    }
}