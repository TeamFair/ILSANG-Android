package com.ilsangtech.ilsang.core.data.title.datasource

import com.ilsangtech.ilsang.core.network.api.TitleApiService
import com.ilsangtech.ilsang.core.network.model.title.TitleListResponse

class TitleDataSourceImpl(private val titleApiService: TitleApiService) : TitleDataSource {
    override suspend fun getTitleList(): TitleListResponse {
        return titleApiService.getTitleList()
    }
}