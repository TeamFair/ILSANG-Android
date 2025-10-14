package com.ilsangtech.ilsang.core.ui.mission.model

enum class MissionTypes {
    IMAGE {
        override fun toString() = "사진 인증"
    },
    OX {
        override fun toString() = "OX"
    },
    WORDS {
        override fun toString() = "서술형"
    }
}