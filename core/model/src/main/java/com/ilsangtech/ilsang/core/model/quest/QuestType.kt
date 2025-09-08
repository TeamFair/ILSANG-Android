package com.ilsangtech.ilsang.core.model.quest

sealed interface QuestType {
    data object Normal : QuestType
    sealed interface Repeat : QuestType {
        data object Daily : Repeat
        data object Weekly : Repeat
        data object Monthly : Repeat
    }

    data object Event : QuestType
}