package com.ilsangtech.ilsang.core.data.title.repository

import com.ilsangtech.ilsang.core.data.title.datasource.TitleDataSource
import com.ilsangtech.ilsang.core.data.title.mapper.toTitleDetail
import com.ilsangtech.ilsang.core.data.title.mapper.toUserTitle
import com.ilsangtech.ilsang.core.domain.TitleRepository
import com.ilsangtech.ilsang.core.model.title.TitleDetail
import com.ilsangtech.ilsang.core.model.title.UserTitle
import com.ilsangtech.ilsang.core.network.model.title.TitleDetailNetworkModel
import com.ilsangtech.ilsang.core.network.model.title.UserTitleNetworkModel

class TitleRepositoryImpl(
    private val titleDataSource: TitleDataSource
) : TitleRepository {
    override suspend fun getTitleList(): List<TitleDetail> {
        return titleDataSource.getTitleList().map(TitleDetailNetworkModel::toTitleDetail)
    }

    override suspend fun getUserTitleList(): List<UserTitle> {
        return titleDataSource.getUserTitleList().map(UserTitleNetworkModel::toUserTitle)
    }

    override suspend fun getUnreadTitleList(): List<UserTitle> {
        return titleDataSource.getUnreadTitleList().map(UserTitleNetworkModel::toUserTitle)
    }
}