package com.ilsangtech.ilsang.feature.iszone

import com.ilsangtech.ilsang.core.model.area.CommercialArea
import com.ilsangtech.ilsang.core.model.area.MetroArea

data class IsZoneUiState(
    val selectedMetroArea: MetroArea?,
    val selectedCommercialArea: CommercialArea?,
    val areaList: List<MetroArea>
)
