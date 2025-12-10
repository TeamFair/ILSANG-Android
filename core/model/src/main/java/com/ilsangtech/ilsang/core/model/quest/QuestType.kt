package com.ilsangtech.ilsang.core.model.quest

import kotlinx.serialization.Serializable

@Serializable
sealed interface QuestType {
    @Serializable
    data object Normal : QuestType

    @Serializable
    sealed interface Repeat : QuestType {
        @Serializable
        data object Daily : Repeat

        @Serializable
        data object Weekly : Repeat

        @Serializable
        data object Monthly : Repeat
    }

    @Serializable
    data object Event : QuestType

    companion object
}