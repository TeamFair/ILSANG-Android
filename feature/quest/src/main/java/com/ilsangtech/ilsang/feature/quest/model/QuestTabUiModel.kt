package com.ilsangtech.ilsang.feature.quest.model

enum class QuestTabUiModel {
    NORMAL {
        override fun toString() = "기본"
    },
    REPEAT {
        override fun toString() = "반복"
    },
    EVENT {
        override fun toString() = "이벤트"
    },
    COMPLETED {
        override fun toString() = "완료"
    }
}
