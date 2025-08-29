package com.ilsangtech.ilsang.feature.my.screens.mytab.model

import com.ilsangtech.ilsang.core.ui.user.model.UserCommercialPointUiModel
import com.ilsangtech.ilsang.core.ui.user.model.UserObtainedPointUiModel

sealed interface MyTabScreenUiState {
    data object Loading : MyTabScreenUiState
    data class Success(
        val myProfileInfo: MyProfileInfoUiModel,
        val myCommercialPoint: UserCommercialPointUiModel,
        val myObtainedPoint: UserObtainedPointUiModel,
        val myPointSummary: MyPointSummaryUiModel
    ) : MyTabScreenUiState

    data class Error(val message: String) : MyTabScreenUiState
}
