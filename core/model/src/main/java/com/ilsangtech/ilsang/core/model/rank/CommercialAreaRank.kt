package com.ilsangtech.ilsang.core.model.rank

data class CommercialAreaRank(
    val commericalAreaCode: String,
    val areaName: String,
    val point: Int,
    val images: List<String>
)
