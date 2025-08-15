package com.ilsangtech.ilsang.feature.banner

import com.ilsangtech.ilsang.core.model.quest.BannerQuest

data class BannerDetailUiState(
    val id: Int,
    val title: String,
    val description: String,
    val imageId: String,
    val selectedQuestType: BannerDetailQuestType,
    val selectedSortType: BannerDetailSortType,
    val bannerQuestUiState: BannerQuestUiState
)

sealed interface BannerQuestUiState {
    data object Loading : BannerQuestUiState
    data class Success(
        val onGoingQuests: List<BannerQuest>,
        val completedQuests: List<BannerQuest>
    ) : BannerQuestUiState

    data object Error : BannerQuestUiState
}

enum class BannerDetailQuestType {
    OnGoing {
        override fun toString(): String {
            return "진행중"
        }
    },
    Completed {
        override fun toString(): String {
            return "완료"
        }
    }
}

enum class BannerDetailSortType {
    PointAsc {
        override fun toString(): String {
            return "포인트 낮은순"
        }
    },

    PointDesc {
        override fun toString(): String {
            return "포인트 높은순"
        }
    },

    ExpiredDate {
        override fun toString(): String {
            return "임박순"
        }
    },

    Popular {
        override fun toString(): String {
            return "인기순"
        }
    }
}