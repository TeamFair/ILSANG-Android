package com.ilsangtech.ilsang.core.model.area

data class MetroArea(
    val code: String,
    val areaName: String,
    val commercialAreaList: List<CommercialArea>
)
