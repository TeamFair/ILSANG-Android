package com.ilsangtech.ilsang.core.model.mission

import kotlinx.serialization.Serializable

@Serializable
sealed interface MissionType {
    @Serializable
    data object Photo : MissionType

    @Serializable
    data object Ox : MissionType

    @Serializable
    data object Words : MissionType
}