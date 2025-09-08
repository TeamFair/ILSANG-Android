package com.ilsangtech.ilsang.core.data.title.datasource

import com.ilsangtech.ilsang.core.network.model.title.TitleDetailNetworkModel
import com.ilsangtech.ilsang.core.network.model.title.UserTitleNetworkModel

interface TitleDataSource {
    suspend fun getTitleList(): List<TitleDetailNetworkModel>

    suspend fun getUserTitleList(): List<UserTitleNetworkModel>

    suspend fun getUnreadTitleList(): List<UserTitleNetworkModel>

    suspend fun readTitle(titleHistoryId: Int)
}