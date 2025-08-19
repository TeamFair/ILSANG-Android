package com.ilsangtech.ilsang.core.data.area.mapper

import com.ilsangtech.ilsang.core.model.area.CommercialArea
import com.ilsangtech.ilsang.core.model.area.MetroArea
import com.ilsangtech.ilsang.core.network.model.area.CommercialAreaNetworkModel
import com.ilsangtech.ilsang.core.network.model.area.MetroAreaNetworkModel

fun CommercialAreaNetworkModel.toCommercialArea(): CommercialArea {
    return CommercialArea(
        code = code,
        areaName = areaName,
        description = description,
        metroAreaCode = metroAreaCode
    )
}

fun MetroAreaNetworkModel.toMetroArea(): MetroArea {
    return MetroArea(
        code = code,
        areaName = areaName,
        commercialAreaList = commercialAreaNetworkModel.map { it.toCommercialArea() }
    )
}