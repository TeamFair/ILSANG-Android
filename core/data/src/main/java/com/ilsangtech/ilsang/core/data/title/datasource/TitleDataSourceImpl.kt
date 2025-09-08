package com.ilsangtech.ilsang.core.data.title.datasource

import com.ilsangtech.ilsang.core.network.api.TitleApiService
import com.ilsangtech.ilsang.core.network.model.title.TitleDetailNetworkModel
import com.ilsangtech.ilsang.core.network.model.title.UserTitleNetworkModel

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
}