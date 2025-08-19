package com.ilsangtech.ilsang.feature.myzone

import com.ilsangtech.ilsang.core.model.area.CommercialArea
import com.ilsangtech.ilsang.core.model.area.MetroArea

data class MyZoneUiState(
    val selectedMetroArea: MetroArea?,
    val selectedCommercialArea: CommercialArea?,
    val areaList: List<MetroArea>,
    val isMyZoneUpdateSuccess: Boolean? = null
)
