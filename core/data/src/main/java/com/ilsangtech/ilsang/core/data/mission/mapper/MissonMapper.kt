package com.ilsangtech.ilsang.core.data.mission.mapper

import com.ilsangtech.ilsang.core.model.mission.Mission
import com.ilsangtech.ilsang.core.network.model.mission.MissionNetworkModel

internal fun MissionNetworkModel.toMission(): Mission {
    return Mission(
        id = id,
        exampleImageIds = exampleImageIds,
        title = title,
        type = type
    )
}