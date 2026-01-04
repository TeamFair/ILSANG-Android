package com.ilsangtech.ilsang.feature.approval.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface MissionExecutionUiState {
    @Serializable
    data object Available : MissionExecutionUiState

    @Serializable
    data object Expired : MissionExecutionUiState

    @Serializable
    data object Completed : MissionExecutionUiState
}