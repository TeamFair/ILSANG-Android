package com.ilsangtech.ilsang.core.data.mission.mapper

import com.ilsangtech.ilsang.core.data.title.mapper.toTitle
import com.ilsangtech.ilsang.core.model.mission.RandomMissionHistory
import com.ilsangtech.ilsang.core.model.mission.RandomMissionHistoryUser
import com.ilsangtech.ilsang.core.network.model.mission.RandomMissionHistoryNetworkModel
import com.ilsangtech.ilsang.core.network.model.mission.RandomMissionHistoryUserNetworkModel

internal fun RandomMissionHistoryNetworkModel.toRandomMissionHistory(): RandomMissionHistory {
    return RandomMissionHistory(
        commercialAreaCode = commercialAreaCode,
        createdAt = createdAt,
        currentUserEmojis = currentUserEmojis,
        hateCount = hateCount,
        likeCount = likeCount,
        missionHistoryId = missionHistoryId,
        submitImageId = submitImageId,
        title = title,
        user = user.toRandomMissionHistoryUser(),
        viewCount = viewCount
    )
}

private fun RandomMissionHistoryUserNetworkModel.toRandomMissionHistoryUser(): RandomMissionHistoryUser {
    return RandomMissionHistoryUser(
        userId = userId,
        nickname = nickname,
        profileImageId = profileImageId,
        title = title?.toTitle()
    )
}