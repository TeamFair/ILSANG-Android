package com.ilsangtech.ilsang.core.data.mission.mapper

import com.ilsangtech.ilsang.core.model.mission.MissionType

internal fun MissionType.Companion.fromString(type: String): MissionType {
    return when (type) {
        "PHOTO" -> MissionType.Photo
        "OX" -> MissionType.Ox
        "WORDS" -> MissionType.Words
        else -> throw IllegalArgumentException("Unknown mission type: $type")
    }
}