package com.ilsangtech.ilsang.core.data.title.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ilsangtech.ilsang.core.data.title.LegendTitleRankPagingSource
import com.ilsangtech.ilsang.core.network.api.TitleApiService
import com.ilsangtech.ilsang.core.network.model.title.LegendTitleRankNetworkModel
import com.ilsangtech.ilsang.core.network.model.title.TitleDetailNetworkModel
import com.ilsangtech.ilsang.core.network.model.title.UserTitleNetworkModel
import kotlinx.coroutines.flow.Flow

class TitleDataSourceImpl(private val titleApiService: TitleApiService) : TitleDataSource {
    override suspend fun getTitleList(): List<TitleDetailNetworkModel> {
        return titleApiService.getTitleList()
    }

    override suspend fun getUserTitleList(): List<UserTitleNetworkModel> {
        return titleApiService.getUserTitleList()
    }

    override suspend fun getUnreadTitleList(): List<UserTitleNetworkModel> {
        return titleApiService.getUnreadTitleList()
    }

    override suspend fun readTitle(titleHistoryId: Int) {
        return titleApiService.readTitle(titleHistoryId)
    }

    override fun getLegendTitleRankList(titleId: String): Flow<PagingData<LegendTitleRankNetworkModel>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                LegendTitleRankPagingSource(
                    titleId = titleId,
                    titleApiService = titleApiService
                )
            }
        ).flow
    }
}