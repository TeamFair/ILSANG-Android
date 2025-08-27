package com.ilsangtech.ilsang.core.data.mission.mapper

import com.ilsangtech.ilsang.core.data.title.mapper.toTitle
import com.ilsangtech.ilsang.core.model.mission.MissionHistoryUser
import com.ilsangtech.ilsang.core.model.mission.RandomMissionHistory
import com.ilsangtech.ilsang.core.network.model.mission.MissionHistoryUserNetworkModel
import com.ilsangtech.ilsang.core.network.model.mission.RandomMissionHistoryNetworkModel

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
        user = user.toMissionHistoryUser(),
        viewCount = viewCount
    )
}

private fun MissionHistoryUserNetworkModel.toMissionHistoryUser(): MissionHistoryUser {
    return MissionHistoryUser(
        userId = userId,
        nickname = nickname,
        profileImageId = profileImageId,
        title = title?.toTitle()
    )
}