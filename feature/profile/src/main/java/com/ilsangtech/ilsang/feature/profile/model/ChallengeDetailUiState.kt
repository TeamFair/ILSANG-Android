package com.ilsangtech.ilsang.feature.profile.model

import com.ilsangtech.ilsang.core.model.mission.UserMissionHistoryDetail

sealed interface ChallengeDetailUiState {
    data object Loading : ChallengeDetailUiState
    data class Success(val data: UserMissionHistoryDetail) : ChallengeDetailUiState
    data object Error : ChallengeDetailUiState
}