package com.ilsangtech.ilsang.core.data.title.datasource

import com.ilsangtech.ilsang.core.network.model.title.TitleDetailNetworkModel

interface TitleDataSource {
    suspend fun getTitleList(): List<TitleDetailNetworkModel>
}