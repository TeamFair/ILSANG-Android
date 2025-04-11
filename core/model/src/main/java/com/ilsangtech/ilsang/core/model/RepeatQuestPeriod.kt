package com.ilsangtech.ilsang.core.model

sealed interface RepeatQuestPeriod {
    data object DAILY : RepeatQuestPeriod
    data object WEEKLY : RepeatQuestPeriod
    data object MONTHLY : RepeatQuestPeriod
}
