package com.ilsangtech.ilsang.feature.profile

import com.ilsangtech.ilsang.core.model.UserInfo
import com.ilsangtech.ilsang.core.model.UserXpStats

sealed interface ProfileUiState {
    data object Loading : ProfileUiState
    data class Success(
        val userInfo: UserInfo,
        val userXpStats: UserXpStats
    ) : ProfileUiState

    data object Error : ProfileUiState

}