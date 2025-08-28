package com.ilsangtech.ilsang.feature.profile.model

import com.ilsangtech.ilsang.core.model.user.UserPoint
import com.ilsangtech.ilsang.core.ui.user.model.UserCommercialPointUiModel

sealed interface ProfileUiState {
    data object Loading : ProfileUiState
    data class Success(
        val userProfileInfo: UserProfileInfoUiModel,
        val userCommercialPoint: UserCommercialPointUiModel,
        val userPoint: UserPoint
    ) : ProfileUiState

    data object Error : ProfileUiState
}