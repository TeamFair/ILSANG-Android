package com.ilsangtech.ilsang.core.model.rank

import com.ilsangtech.ilsang.core.model.title.Title

data class UserRank(
    val userId: String,
    val nickName: String,
    val point: Int,
    val profileImageId: String?,
    val rank: Int,
    val title: Title
)