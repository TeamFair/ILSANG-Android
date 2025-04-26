package com.ilsangtech.ilsang.core.data.challenge.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ilsangtech.ilsang.core.data.challenge.datasource.ChallengeDataSource
import com.ilsangtech.ilsang.core.data.challenge.toChallenge
import com.ilsangtech.ilsang.core.domain.ChallengeRepository
import com.ilsangtech.ilsang.core.domain.ImageRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.Challenge
import com.ilsangtech.ilsang.core.network.model.challenge.ChallengeNetworkModel

class ChallengeRepositoryImpl(
    private val userRepository: UserRepository,
    private val imageRepository: ImageRepository,
    private val challengeDataSource: ChallengeDataSource
) : ChallengeRepository {
    override suspend fun getChallengesWithTotal(
        page: Int,
        size: Int
    ): Pair<List<Challenge>, Int> {
        val response = challengeDataSource.getChallenges(
            authorization = userRepository.currentUser?.authorization!!,
            status = "APPROVED",
            userId = null,
            userDataOnly = true,
            questId = null,
            page = page,
            size = size
        )
        return response.challengeList.map(ChallengeNetworkModel::toChallenge) to response.total
    }

    override fun getChallengePaging(): PagingSource<Int, Challenge> =
        object : PagingSource<Int, Challenge>() {
            override fun getRefreshKey(state: PagingState<Int, Challenge>): Int? {
                return state.anchorPosition?.let { anchorPosition ->
                    val anchorPage = state.closestPageToPosition(anchorPosition)
                    anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
                }
            }

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Challenge> {
                return try {
                    val pageNumber = params.key ?: 0
                    val size = params.loadSize
                    val challengesWithTotal = getChallengesWithTotal(pageNumber, size)

                    val challenges = challengesWithTotal.first
                    val total = challengesWithTotal.second
                    return LoadResult.Page(
                        data = challenges,
                        prevKey = null,
                        nextKey = if ((pageNumber + 1) * size < total) pageNumber + 1 else null
                    )
                } catch (e: Exception) {
                    LoadResult.Error(e)
                }
            }
        }

    override suspend fun submitChallenge(imageBytes: ByteArray, questId: String): String {
        val imageId = imageRepository.uploadImage(imageBytes)
        return challengeDataSource.submitChallenge(
            authorization = userRepository.currentUser?.authorization!!,
            questId = questId,
            imageId = imageId
        ).challengeSubmitData.challengeId
    }
}