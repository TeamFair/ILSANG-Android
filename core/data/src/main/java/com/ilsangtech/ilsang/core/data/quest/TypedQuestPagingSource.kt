package com.ilsangtech.ilsang.core.data.quest

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ilsangtech.ilsang.core.network.api.QuestApiService
import com.ilsangtech.ilsang.core.network.model.quest.TypedQuestNetworkModel

class TypedQuestPagingSource(
    private val questApiService: QuestApiService,
    private val commercialAreaCode: String,
    private val type: String?,
    private val repeatFrequency: String?,
    private val orderExpiredDesc: Boolean?,
    private val orderRewardDesc: Boolean?,
    private val favoriteYn: Boolean?,
    private val completedYn: Boolean?
) : PagingSource<Int, TypedQuestNetworkModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TypedQuestNetworkModel> {
        return try {
            val pageNumber = params.key ?: 0
            val response = questApiService.getTypedQuests(
                commercialAreaCode = commercialAreaCode,
                type = type,
                repeatFrequency = repeatFrequency,
                orderExpiredDesc = orderExpiredDesc,
                orderRewardDesc = orderRewardDesc,
                favoriteYn = favoriteYn,
                completedYn = completedYn,
                page = pageNumber,
                size = params.loadSize,
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

    override fun getRefreshKey(state: PagingState<Int, TypedQuestNetworkModel>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}