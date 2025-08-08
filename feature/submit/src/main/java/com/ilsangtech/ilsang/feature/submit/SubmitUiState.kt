package com.ilsangtech.ilsang.feature.submit

import com.ilsangtech.ilsang.core.model.Reward

sealed interface SubmitUiState {
    data object NotSubmitted : SubmitUiState
    data object Loading : SubmitUiState
    data class Success(val rewardList: List<Reward>) : SubmitUiState
    data object Error : SubmitUiState
}