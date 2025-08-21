package com.ilsangtech.ilsang.core.data.season.datasource

import com.ilsangtech.ilsang.core.network.api.SeasonApiService
import com.ilsangtech.ilsang.core.network.model.season.SeasonNetworkModel

class SeasonDataSourceImpl(
    private val seasonApiService: SeasonApiService
) : SeasonDataSource {
    override suspend fun getSeasonList(): List<SeasonNetworkModel> {
        return seasonApiService.getSeasonList()
    }
}