package com.ilsangtech.ilsang.core.data.title

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ilsangtech.ilsang.core.network.api.TitleApiService
import com.ilsangtech.ilsang.core.network.model.title.LegendTitleRankNetworkModel

class LegendTitleRankPagingSource(
    private val titleId: String,
    private val titleApiService: TitleApiService
) : PagingSource<Int, LegendTitleRankNetworkModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LegendTitleRankNetworkModel> {
        return try {
            val pageNumber = params.key ?: 0
            val response = titleApiService.getLegendTitleRankList(
                titleId = titleId,
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

    override fun getRefreshKey(state: PagingState<Int, LegendTitleRankNetworkModel>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}