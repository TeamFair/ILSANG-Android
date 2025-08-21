package com.ilsangtech.ilsang.core.model.title

sealed interface TitleType {
    data object Metro : TitleType
    data object Commercial : TitleType
    data object Contribution : TitleType
}