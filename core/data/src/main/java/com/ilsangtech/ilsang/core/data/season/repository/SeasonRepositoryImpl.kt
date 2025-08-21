package com.ilsangtech.ilsang.core.data.season.repository

import com.ilsangtech.ilsang.core.data.season.datasource.SeasonDataSource
import com.ilsangtech.ilsang.core.data.season.mapper.toSeason
import com.ilsangtech.ilsang.core.domain.SeasonRepository
import com.ilsangtech.ilsang.core.model.season.Season
import com.ilsangtech.ilsang.core.network.model.season.SeasonNetworkModel

class SeasonRepositoryImpl(
    private val seasonDataSource: SeasonDataSource
) : SeasonRepository {
    override suspend fun getSeasonList(): List<Season> {
        return seasonDataSource.getSeasonList().map(SeasonNetworkModel::toSeason)
    }
}