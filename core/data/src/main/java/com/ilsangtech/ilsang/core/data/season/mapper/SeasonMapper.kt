package com.ilsangtech.ilsang.core.data.season.mapper

import com.ilsangtech.ilsang.core.model.season.Season
import com.ilsangtech.ilsang.core.network.model.season.SeasonNetworkModel

internal fun SeasonNetworkModel.toSeason(): Season {
    return Season(
        id = id,
        seasonNumber = seasonNumber,
        startDate = startDate,
        endDate = endDate
    )
}