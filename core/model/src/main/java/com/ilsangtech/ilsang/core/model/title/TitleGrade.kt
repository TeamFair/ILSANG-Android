package com.ilsangtech.ilsang.core.model.title

sealed interface TitleGrade {
    data object Standard : TitleGrade
    data object Rare : TitleGrade
    data object Legend : TitleGrade
}