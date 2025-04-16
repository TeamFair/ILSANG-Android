package com.ilsangtech.ilsang.core.data.challenge.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ilsangtech.ilsang.core.domain.ChallengeRepository
import com.ilsangtech.ilsang.core.model.Challenge

class ChallengePagingSource(
    private val challengeRepository: ChallengeRepository
) : PagingSource<Int, Challenge>() {
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
            val challenges = challengeRepository.getChallenges(pageNumber, size)
            return LoadResult.Page(
                data = challenges,
                prevKey = null,
                nextKey = pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}