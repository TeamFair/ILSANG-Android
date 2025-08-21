package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.area.MetroArea
import kotlinx.coroutines.flow.Flow

interface AreaRepository {
    fun getMetroAreaList(): Flow<List<MetroArea>>

    fun getCommercialName(commericalCode: String): Flow<String>
}