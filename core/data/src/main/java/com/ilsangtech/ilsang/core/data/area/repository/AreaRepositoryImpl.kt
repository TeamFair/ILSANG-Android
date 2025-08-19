package com.ilsangtech.ilsang.core.data.area.repository

import com.ilsangtech.ilsang.core.data.area.datasource.AreaDataSource
import com.ilsangtech.ilsang.core.data.area.mapper.toMetroArea
import com.ilsangtech.ilsang.core.domain.AreaRepository
import com.ilsangtech.ilsang.core.model.area.MetroArea
import com.ilsangtech.ilsang.core.network.model.area.MetroAreaNetworkModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class AreaRepositoryImpl(
    private val areaDataSource: AreaDataSource
) : AreaRepository {
    private var areaList: List<MetroArea> = emptyList()

    override fun getAreaList(): Flow<List<MetroArea>> = flow {
        if (areaList.isEmpty()) {
            areaDataSource.getAreaList().metroAreaList
                .map(MetroAreaNetworkModel::toMetroArea)
        }
        emit(areaList)
    }

    override fun getCommercialName(commericalCode: String): Flow<String> {
        return getAreaList().map { areaList ->
            val commericalAreaList = areaList.flatMap { it.commercialAreaList }
            commericalAreaList.first { it.code == commericalCode }.areaName
        }
    }
}