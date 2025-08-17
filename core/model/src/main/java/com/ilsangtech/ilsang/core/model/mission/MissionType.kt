package com.ilsangtech.ilsang.core.model.mission

sealed interface MissionType {
    data object Photo : MissionType
    sealed interface Quiz : MissionType {
        data object Ox : Quiz
        data object Words : Quiz
    }
}