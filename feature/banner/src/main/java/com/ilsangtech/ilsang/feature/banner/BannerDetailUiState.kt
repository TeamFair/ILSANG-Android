package com.ilsangtech.ilsang.feature.banner

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