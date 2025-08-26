package com.ilsangtech.ilsang.feature.submit

import com.ilsangtech.ilsang.core.model.RewardPoint

sealed interface SubmitUiState {
    data object NotSubmitted : SubmitUiState
    data object Loading : SubmitUiState
    data class Success(val rewardPoints: List<RewardPoint>) : SubmitUiState
    data object Error : SubmitUiState
}