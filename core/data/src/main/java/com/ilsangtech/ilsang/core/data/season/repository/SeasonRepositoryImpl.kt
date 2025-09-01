package com.ilsangtech.ilsang.core.data.season.repository

import com.ilsangtech.ilsang.core.data.season.datasource.SeasonDataSource
import com.ilsangtech.ilsang.core.data.season.mapper.toSeason
import com.ilsangtech.ilsang.core.domain.SeasonRepository
import com.ilsangtech.ilsang.core.model.season.Season
import com.ilsangtech.ilsang.core.network.model.season.SeasonNetworkModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SeasonRepositoryImpl(
    private val seasonDataSource: SeasonDataSource
) : SeasonRepository {
    override suspend fun getSeasonList(): List<Season> {
        return seasonDataSource.getSeasonList().map(SeasonNetworkModel::toSeason)
    }

    override suspend fun getCurrentSeason(): Season {
        val seasonList = getSeasonList()
        return seasonList.firstOrNull {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val start = simpleDateFormat.parse(it.startDate)
            val end = simpleDateFormat.parse(it.endDate)
            val current = Date()

            current.after(start) && current.before(end)
        } ?: seasonList.last()
    }
}