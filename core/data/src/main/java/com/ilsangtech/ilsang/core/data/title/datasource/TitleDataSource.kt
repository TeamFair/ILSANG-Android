package com.ilsangtech.ilsang.core.data.title.datasource

import com.ilsangtech.ilsang.core.network.model.title.TitleListResponse

interface TitleDataSource {
    suspend fun getTitleList(): TitleListResponse
}