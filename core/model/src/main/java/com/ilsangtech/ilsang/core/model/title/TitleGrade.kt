package com.ilsangtech.ilsang.core.model.title

import kotlinx.serialization.Serializable

@Serializable
sealed interface TitleGrade {
    @Serializable
    data object Standard : TitleGrade

    @Serializable
    data object Rare : TitleGrade

    @Serializable
    data object Legend : TitleGrade
}