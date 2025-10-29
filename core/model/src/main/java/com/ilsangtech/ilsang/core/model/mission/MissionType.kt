package com.ilsangtech.ilsang.core.model.mission

sealed interface MissionType {
    data object Photo : MissionType
    data object Ox : MissionType
    data object Words : MissionType

    companion object
}