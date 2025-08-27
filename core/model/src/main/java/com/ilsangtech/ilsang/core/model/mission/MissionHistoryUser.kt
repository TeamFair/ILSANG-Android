package com.ilsangtech.ilsang.core.model.mission

import com.ilsangtech.ilsang.core.model.title.Title

data class MissionHistoryUser(
    val userId: String,
    val nickname: String,
    val profileImageId: String?,
    val title: Title?
)
