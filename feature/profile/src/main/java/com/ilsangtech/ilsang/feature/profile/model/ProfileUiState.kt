package com.ilsangtech.ilsang.feature.profile.model

import com.ilsangtech.ilsang.core.ui.user.model.UserCommercialPointUiModel
import com.ilsangtech.ilsang.core.ui.user.model.UserObtainedPointUiModel

sealed interface ProfileUiState {
    data object Loading : ProfileUiState
    data class Success(
        val userProfileInfo: UserProfileInfoUiModel,
        val userCommercialPoint: UserCommercialPointUiModel,
        val userPoint: UserObtainedPointUiModel
    ) : ProfileUiState

    data object Error : ProfileUiState
}