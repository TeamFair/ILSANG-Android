package com.ilsangtech.ilsang.core.model

enum class QuestType(val title: String) {
    NORMAL("기본"),
    REPEAT("반복"),
    EVENT("이벤트"),
    COMPLETED("완료")
}

sealed interface NewQuestType {
    data object Normal : NewQuestType
    sealed interface Repeat : NewQuestType {
        data object Daily : Repeat
        data object Weekly : Repeat
        data object Monthly : Repeat
    }

    data object Event : NewQuestType
}