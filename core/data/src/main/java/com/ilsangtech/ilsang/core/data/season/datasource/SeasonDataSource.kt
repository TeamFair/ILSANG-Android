package com.ilsangtech.ilsang.core.data.season.datasource

import com.ilsangtech.ilsang.core.network.model.season.SeasonNetworkModel

interface SeasonDataSource {
    suspend fun getSeasonList(): List<SeasonNetworkModel>
}