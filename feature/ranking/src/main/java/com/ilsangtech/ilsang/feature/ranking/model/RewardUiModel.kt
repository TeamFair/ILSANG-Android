package com.ilsangtech.ilsang.feature.ranking.model

enum class RewardUiModel {
    Metro {
        override fun toString(): String {
            return "일상지역"
        }
    },
    Commercial {
        override fun toString(): String {
            return "일상존"
        }
    },
    Contribution {
        override fun toString(): String {
            return "기여도"
        }
    }
}