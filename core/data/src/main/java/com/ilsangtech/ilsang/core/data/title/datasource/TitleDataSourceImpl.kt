package com.ilsangtech.ilsang.core.data.title.datasource

import com.ilsangtech.ilsang.core.network.api.TitleApiService
import com.ilsangtech.ilsang.core.network.model.title.TitleDetailNetworkModel

class TitleDataSourceImpl(private val titleApiService: TitleApiService) : TitleDataSource {
    override suspend fun getTitleList(): List<TitleDetailNetworkModel> {
        return titleApiService.getTitleList()
    }
}