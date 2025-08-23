package com.ilsangtech.ilsang.core.data.quest

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ilsangtech.ilsang.core.network.api.QuestApiService
import com.ilsangtech.ilsang.core.network.model.quest.BannerQuestNetworkModel

class BannerQuestPagingSource(
    private val questApiService: QuestApiService,
    private val bannerId: Int,
    private val orderExpiredDesc: Boolean?,
    private val orderRewardDesc: Boolean?
) : PagingSource<Int, BannerQuestNetworkModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BannerQuestNetworkModel> {
        return try {
            val pageNumber = params.key ?: 0
            val response = questApiService.getBannerQuests(
                bannerId = bannerId,
                orderExpiredDesc = orderExpiredDesc,
                orderRewardDesc = orderRewardDesc,
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

    override fun getRefreshKey(state: PagingState<Int, BannerQuestNetworkModel>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}