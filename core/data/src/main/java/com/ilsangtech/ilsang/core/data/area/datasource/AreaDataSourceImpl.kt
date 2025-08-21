package com.ilsangtech.ilsang.core.data.area.datasource

import com.ilsangtech.ilsang.core.network.api.AreaApiService
import com.ilsangtech.ilsang.core.network.model.area.CommercialAreaNetworkModel
import com.ilsangtech.ilsang.core.network.model.area.MetroAreaNetworkModel

class AreaDataSourceImpl(
    private val areaApiService: AreaApiService
) : AreaDataSource {
    override suspend fun getMetroAreaList(): List<MetroAreaNetworkModel> {
        return areaApiService.getMetroAreaList()
    }

    override suspend fun getMetroArea(metroAreaCode: String): MetroAreaNetworkModel {
        return areaApiService.getMetroAreaDetail(metroAreaCode)
    }

    override suspend fun geetCommercialArea(commercialAreaCode: String): CommercialAreaNetworkModel {
        return areaApiService.getCommercialAreaDetail(commercialAreaCode)
    }
}