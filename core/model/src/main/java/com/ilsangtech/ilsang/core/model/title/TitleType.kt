package com.ilsangtech.ilsang.core.model.title

import kotlinx.serialization.Serializable

@Serializable
sealed interface TitleType {
    @Serializable
    data object None : TitleType

    @Serializable
    data object Metro : TitleType

    @Serializable
    data object Commercial : TitleType

    @Serializable
    data object Contribution : TitleType
}