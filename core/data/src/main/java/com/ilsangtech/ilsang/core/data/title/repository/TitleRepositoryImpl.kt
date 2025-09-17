package com.ilsangtech.ilsang.core.data.title.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.ilsangtech.ilsang.core.data.title.datasource.TitleDataSource
import com.ilsangtech.ilsang.core.data.title.mapper.toLegendTitle
import com.ilsangtech.ilsang.core.data.title.mapper.toSeasonRewardTitle
import com.ilsangtech.ilsang.core.data.title.mapper.toTitleDetail
import com.ilsangtech.ilsang.core.data.title.mapper.toUserTitle
import com.ilsangtech.ilsang.core.domain.TitleRepository
import com.ilsangtech.ilsang.core.model.title.LegendTitle
import com.ilsangtech.ilsang.core.model.title.SeasonRewardTitle
import com.ilsangtech.ilsang.core.model.title.TitleDetail
import com.ilsangtech.ilsang.core.model.title.TitleType
import com.ilsangtech.ilsang.core.model.title.UserTitle
import com.ilsangtech.ilsang.core.network.model.title.LegendTitleRankNetworkModel
import com.ilsangtech.ilsang.core.network.model.title.SeasonRewardTitleNetworkModel
import com.ilsangtech.ilsang.core.network.model.title.TitleDetailNetworkModel
import com.ilsangtech.ilsang.core.network.model.title.UserTitleNetworkModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

    override suspend fun readTitle(titleHistoryId: Int): Result<Unit> {
        return runCatching { titleDataSource.readTitle(titleHistoryId) }
    }

    override fun getLegendTitleRankList(titleId: String): Flow<PagingData<LegendTitle>> {
        return titleDataSource.getLegendTitleRankList(titleId).map { pagingData ->
            pagingData.map(LegendTitleRankNetworkModel::toLegendTitle)
        }
    }

    override suspend fun getSeasonRewardTitleList(type: TitleType): List<SeasonRewardTitle> {
        return titleDataSource.getSeasonRewardTitleList(
            when (type) {
                TitleType.Metro -> "METRO"
                TitleType.Commercial -> "COMMERCIAL"
                TitleType.Contribution -> "CONTRIBUTION"
                TitleType.None -> "NONE"
            }
        ).map(SeasonRewardTitleNetworkModel::toSeasonRewardTitle)
    }
}