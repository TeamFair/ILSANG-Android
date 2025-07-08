package com.ilsangtech.ilsang.core.data.challenge.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ilsangtech.ilsang.core.network.model.challenge.ChallengeNetworkModel

class ChallengePagingSource(
    private val userId: String? = null,
    private val challengeDataSource: ChallengeDataSource
) : PagingSource<Int, ChallengeNetworkModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ChallengeNetworkModel> {
        return try {
            val pageNumber = params.key ?: 0
            val size = params.loadSize
            val challengesResponse = challengeDataSource.getChallenges(
                status = "APPROVED",
                userId = userId,
                userDataOnly = true,
                questId = null,
                page = pageNumber,
                size = size
            )
            val challenges = challengesResponse.challengeList
            val total = challengesResponse.total

            return LoadResult.Page(
                data = challenges,
                prevKey = null,
                nextKey = if ((pageNumber + 1) * size < total) pageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ChallengeNetworkModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}