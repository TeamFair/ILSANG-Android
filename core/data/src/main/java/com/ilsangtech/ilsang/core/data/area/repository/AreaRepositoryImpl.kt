package com.ilsangtech.ilsang.core.data.area.repository

import com.ilsangtech.ilsang.core.data.area.datasource.AreaDataSource
import com.ilsangtech.ilsang.core.data.area.mapper.toMetroArea
import com.ilsangtech.ilsang.core.domain.AreaRepository
import com.ilsangtech.ilsang.core.model.area.CommercialArea
import com.ilsangtech.ilsang.core.model.area.MetroArea
import com.ilsangtech.ilsang.core.network.model.area.MetroAreaNetworkModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AreaRepositoryImpl(
    private val areaDataSource: AreaDataSource
) : AreaRepository {
    private var metroAreaList: List<MetroArea> = emptyList()

    override fun getMetroAreaList(): Flow<List<MetroArea>> = flow {
        if (metroAreaList.isEmpty()) {
            metroAreaList = areaDataSource.getMetroAreaList()
                .map(MetroAreaNetworkModel::toMetroArea)
        }
        emit(metroAreaList)
    }

    override suspend fun getMetroArea(metroAreaCode: String): MetroArea {
        if (metroAreaList.isEmpty()) {
            metroAreaList = areaDataSource.getMetroAreaList()
                .map(MetroAreaNetworkModel::toMetroArea)
        }
        return metroAreaList.first { it.code == metroAreaCode }
    }

    override suspend fun getCommercialArea(commercialAreaCode: String): CommercialArea {
        if (metroAreaList.isEmpty()) {
            metroAreaList = areaDataSource.getMetroAreaList()
                .map(MetroAreaNetworkModel::toMetroArea)
        }
        return metroAreaList.first {
            it.commercialAreaList.any { commercialArea ->
                commercialArea.code == commercialAreaCode
            }
        }.commercialAreaList.first { it.code == commercialAreaCode }
    }
}