package com.ilsangtech.ilsang.core.model.mission

import com.ilsangtech.ilsang.core.model.title.Title
import kotlinx.serialization.Serializable

@Serializable
data class MissionHistoryUser(
    val userId: String,
    val nickname: String,
    val profileImageId: String?,
    val title: Title?
)
