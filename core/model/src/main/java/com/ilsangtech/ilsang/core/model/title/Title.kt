package com.ilsangtech.ilsang.core.model.title

import kotlinx.serialization.Serializable

@Serializable
data class Title(
    val name: String,
    val grade: TitleGrade,
    val type: TitleType,
)
