package com.ilsangtech.ilsang.feature.submit.model

import com.ilsangtech.ilsang.core.model.RewardPoint

sealed interface SubmitResultUiState {
    data object NotSubmitted : SubmitResultUiState
    data object Loading : SubmitResultUiState
    data class Success(val rewardPoints: List<RewardPoint>) : SubmitResultUiState
    data object WrongAnswer : SubmitResultUiState
    data object Error : SubmitResultUiState
}