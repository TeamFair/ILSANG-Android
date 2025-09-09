package com.ilsangtech.ilsang.core.data.title.datasource

import androidx.paging.PagingData
import com.ilsangtech.ilsang.core.network.model.title.LegendTitleRankNetworkModel
import com.ilsangtech.ilsang.core.network.model.title.TitleDetailNetworkModel
import com.ilsangtech.ilsang.core.network.model.title.UserTitleNetworkModel
import kotlinx.coroutines.flow.Flow

interface TitleDataSource {
    suspend fun getTitleList(): List<TitleDetailNetworkModel>

    suspend fun getUserTitleList(): List<UserTitleNetworkModel>

    suspend fun getUnreadTitleList(): List<UserTitleNetworkModel>

    suspend fun readTitle(titleHistoryId: Int)

    fun getLegendTitleRankList(titleId: String): Flow<PagingData<LegendTitleRankNetworkModel>>
}