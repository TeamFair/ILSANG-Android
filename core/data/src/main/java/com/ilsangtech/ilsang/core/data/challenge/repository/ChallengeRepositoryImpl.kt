package com.ilsangtech.ilsang.core.data.challenge.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.map
import com.ilsangtech.ilsang.core.data.challenge.datasource.ChallengeDataSource
import com.ilsangtech.ilsang.core.data.challenge.datasource.ChallengePagingSource
import com.ilsangtech.ilsang.core.data.challenge.toChallenge
import com.ilsangtech.ilsang.core.data.challenge.toRandomChallenge
import com.ilsangtech.ilsang.core.domain.ChallengeRepository
import com.ilsangtech.ilsang.core.domain.EmojiRepository
import com.ilsangtech.ilsang.core.domain.ImageRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.Challenge
import com.ilsangtech.ilsang.core.model.RandomChallenge
import com.ilsangtech.ilsang.core.network.model.challenge.ChallengeNetworkModel
import com.ilsangtech.ilsang.core.network.model.challenge.RandomChallengeNetworkModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChallengeRepositoryImpl(
    private val userRepository: UserRepository,
    private val imageRepository: ImageRepository,
    private val challengeDataSource: ChallengeDataSource,
    private val emojiRepository: EmojiRepository
) : ChallengeRepository {

    override fun getChallengePaging(userId: String?): Flow<PagingData<Challenge>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ChallengePagingSource(
                    userId = userId,
                    userRepository = userRepository,
                    challengeDataSource = challengeDataSource
                )
            }
        ).flow.map { pagingData ->
            pagingData.map(ChallengeNetworkModel::toChallenge)
        }
    }

    override suspend fun submitChallenge(imageBytes: ByteArray, questId: String): String {
        val imageId = imageRepository.uploadImage(
            authorization = userRepository.currentUser?.authorization!!,
            type = "RECEIPT",
            imageBytes = imageBytes
        )
        return challengeDataSource.submitChallenge(
            authorization = userRepository.currentUser?.authorization!!,
            questId = questId,
            imageId = imageId
        ).challengeSubmitData.challengeId
    }

    override val randomChallengePagingSource: PagingSource<Int, RandomChallenge>
        get() = object : PagingSource<Int, RandomChallenge>() {
            override fun getRefreshKey(state: PagingState<Int, RandomChallenge>): Int? {
                return state.anchorPosition?.let { anchorPosition ->
                    val anchorPage = state.closestPageToPosition(anchorPosition)
                    anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
                }
            }

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RandomChallenge> {
                return try {
                    val pageNumber = params.key ?: 0
                    val size = params.loadSize
                    val challengesWithTotal = getRandomChallengesWithTotal(pageNumber, size)

                    val challenges = challengesWithTotal.first.map {
                        val emoji = emojiRepository.getEmoji(it.challengeId)
                        it.copy(emoji = emoji)
                    }

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

    override suspend fun getRandomChallengesWithTotal(
        page: Int,
        size: Int
    ): Pair<List<RandomChallenge>, Int> {
        val response = challengeDataSource.getRandomChallenges(
            authorization = userRepository.currentUser?.authorization!!,
            page = page,
            size = size
        )
        val totalSize = response.total
        val randomChallenges =
            response.randomChallengeData.map(RandomChallengeNetworkModel::toRandomChallenge)
        return randomChallenges to totalSize
    }

    override suspend fun deleteChallenge(challengeId: String): Result<Unit> {
        return runCatching {
            challengeDataSource.deleteChallenge(
                authorization = userRepository.currentUser?.authorization!!,
                challengeId = challengeId
            )
        }
    }

    override suspend fun reportChallenge(challengeId: String): Result<Unit> {
        return runCatching {
            challengeDataSource.reportChallenge(
                authorization = userRepository.currentUser?.authorization!!,
                challengeId = challengeId
            )
        }
    }
}