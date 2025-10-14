package com.ilsangtech.ilsang.core.ui.mission.model

enum class MissionSortTypes {
    Latest {
        override fun toString() = "최신순"
    },
    PointDesc {
        override fun toString() = "포인트 높은순"
    },
    PointAsc {
        override fun toString() = "포인트 낮은순"
    }
}