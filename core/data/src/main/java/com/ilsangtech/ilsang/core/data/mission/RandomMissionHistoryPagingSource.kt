package com.ilsangtech.ilsang.core.data.mission

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ilsangtech.ilsang.core.network.api.MissionApiService
import com.ilsangtech.ilsang.core.network.model.mission.RandomMissionHistoryNetworkModel

class RandomMissionHistoryPagingSource(
    private val missionApiService: MissionApiService
) : PagingSource<Int, RandomMissionHistoryNetworkModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RandomMissionHistoryNetworkModel> {
        return try {
            val pageNumber = params.key ?: 0
            val response = missionApiService.getRandomMissionHistory(
                page = pageNumber,
                size = params.loadSize
            )
            val prevKey = if (pageNumber == 0) null else pageNumber - 1
            val nextKey = if (response.isLast) null else pageNumber + 1

            LoadResult.Page(
                data = response.content,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RandomMissionHistoryNetworkModel>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}