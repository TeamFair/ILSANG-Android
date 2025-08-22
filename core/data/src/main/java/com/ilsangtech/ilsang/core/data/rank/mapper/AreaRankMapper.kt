package com.ilsangtech.ilsang.core.data.rank.mapper

import com.ilsangtech.ilsang.core.model.rank.CommercialAreaRank
import com.ilsangtech.ilsang.core.model.rank.MetroAreaRank
import com.ilsangtech.ilsang.core.network.model.rank.CommercialAreaRankNetworkModel
import com.ilsangtech.ilsang.core.network.model.rank.MetroAreaRankNetworkModel

internal fun MetroAreaRankNetworkModel.toMetroAreaRank(): MetroAreaRank {
    return MetroAreaRank(
        metroAreaCode = metroCode,
        areaName = areaName,
        point = point,
        images = images
    )
}

internal fun CommercialAreaRankNetworkModel.toCommercialAreaRank(): CommercialAreaRank {
    return CommercialAreaRank(
        commericalAreaCode = commercialCode,
        areaName = areaName,
        point = point,
        images = images
    )
}