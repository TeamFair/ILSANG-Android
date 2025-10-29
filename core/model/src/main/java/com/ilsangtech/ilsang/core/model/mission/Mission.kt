package com.ilsangtech.ilsang.core.model.mission

data class Mission(
    val id: Int,
    val exampleImageIds: List<String>,
    val title: String,
    val type: MissionType
)