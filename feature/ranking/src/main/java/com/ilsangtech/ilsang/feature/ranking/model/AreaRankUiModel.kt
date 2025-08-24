package com.ilsangtech.ilsang.feature.ranking.model

import com.ilsangtech.ilsang.core.model.rank.CommercialAreaRank
import com.ilsangtech.ilsang.core.model.rank.MetroAreaRank

data class AreaRankUiModel(
    val areaCode: String,
    val areaName: String,
    val rank: Int,
    val point: Int,
    val images: List<String>
)

internal fun MetroAreaRank.toAreaRankUiModel(): AreaRankUiModel {
    return AreaRankUiModel(
        areaCode = metroAreaCode,
        areaName = areaName,
        rank = rank,
        point = point,
        images = images
    )
}

internal fun CommercialAreaRank.toAreaRankUiModel(): AreaRankUiModel {
    return AreaRankUiModel(
        areaCode = commercialAreaCode,
        areaName = areaName,
        rank = rank,
        point = point,
        images = images
    )
}