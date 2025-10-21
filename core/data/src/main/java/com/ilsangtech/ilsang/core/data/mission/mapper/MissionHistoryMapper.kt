package com.ilsangtech.ilsang.core.data.mission.mapper

import com.ilsangtech.ilsang.core.data.title.mapper.toTitle
import com.ilsangtech.ilsang.core.model.mission.ExampleMissionHistory
import com.ilsangtech.ilsang.core.model.mission.MissionHistoryUser
import com.ilsangtech.ilsang.core.model.mission.MissionType
import com.ilsangtech.ilsang.core.model.mission.RandomMissionHistory
import com.ilsangtech.ilsang.core.model.mission.UserMissionHistory
import com.ilsangtech.ilsang.core.network.model.mission.ExampleMissionHistoryNetworkModel
import com.ilsangtech.ilsang.core.network.model.mission.MissionHistoryUserNetworkModel
import com.ilsangtech.ilsang.core.network.model.mission.RandomMissionHistoryNetworkModel
import com.ilsangtech.ilsang.core.network.model.mission.UserMissionHistoryNetworkModel

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

internal fun ExampleMissionHistoryNetworkModel.toExampleMissionHistory(): ExampleMissionHistory {
    return ExampleMissionHistory(
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

internal fun UserMissionHistoryNetworkModel.toUserMissionHistory(): UserMissionHistory {
    return UserMissionHistory(
        missionHistoryId = missionHistoryId,
        title = title,
        submitImageId = submitImageId,
        questImageId = questImageId,
        viewCount = viewCount,
        likeCount = likeCount,
        createdAt = createdAt,
        commercialAreaCode = commercialAreaCode,
        missionType = when (missionType) {
            "PHOTO" -> MissionType.Photo
            "OX" -> MissionType.Ox
            "WORDS" -> MissionType.Words
            else -> throw IllegalArgumentException("Unknown mission type: $missionType")
        }
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