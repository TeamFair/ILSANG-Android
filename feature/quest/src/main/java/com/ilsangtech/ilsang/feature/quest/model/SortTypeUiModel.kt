package com.ilsangtech.ilsang.feature.quest.model

enum class SortTypeUiModel {
    Popular {
        override fun toString(): String = "인기순"
    },
    PointDesc {
        override fun toString(): String = "포인트 높은순"
    },
    PointAsc {
        override fun toString(): String = "포인트 낮은순"
    }
}