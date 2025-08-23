package com.ilsangtech.ilsang.feature.quest.model

enum class RepeatQuestTypeUiModel {
    Daily {
        override fun toString(): String = "일간"
    },
    Weekly {
        override fun toString(): String = "주간"
    },
    Monthly {
        override fun toString(): String = "월간"
    }
}