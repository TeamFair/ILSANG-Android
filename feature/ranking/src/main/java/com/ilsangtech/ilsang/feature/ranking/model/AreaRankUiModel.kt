package com.ilsangtech.ilsang.feature.ranking.model

import com.ilsangtech.ilsang.core.model.rank.CommercialAreaRank
import com.ilsangtech.ilsang.core.model.rank.MetroAreaRank

data class AreaRankUiModel(
    val areaName: String,
    val rank: Int,
    val point: Int,
    val images: List<String>
)

internal fun MetroAreaRank.toAreaRankUiModel(): AreaRankUiModel {
    return AreaRankUiModel(
        areaName = areaName,
        rank = 0, //TODO 수정 필요
        point = point,
        images = images
    )
}

internal fun CommercialAreaRank.toAreaRankUiModel(): AreaRankUiModel {
    return AreaRankUiModel(
        areaName = areaName,
        rank = 0, //TODO 수정 필요
        point = point,
        images = images
    )
}